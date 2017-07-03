package nexus.cpu

import nexus._
import nexus.util._
import shapeless._

/**
 * @author Tongfei Chen
 */
class CPUFloat32 extends Env[DenseTensor, Float] {

  type Handle = UntypedDenseTensor[Float]

  def newTensor[A <: HList](axes: A, shape: Array[Int]) =
    DenseTensor.fromFlatArray(Array.ofDim[Float](ShapeUtils.product(shape)), axes, shape)

  def untype(x: DenseTensor[Float, _]) = x
  def typeOf[A <: HList](x: DenseTensor[Float, A]) = x.axes
  def typeWith[A <: HList](x: UntypedDenseTensor[Float], a: A) = x typeWith a

  def zero = 0f
  def one = 1f

  def map(x: UntypedDenseTensor[Float])(f: Float => Float): UntypedDenseTensor[Float] = {
    import x._
    if (x.rank == 0) return scalar(f(x()))
    val y = new Array[Float](size)
    var yi = 0
    var xi = offset
    var d = rank - 1
    val indices = Array.fill(rank)(0)
    while (yi < size) {
      y(yi) = f(handle(xi))
      xi += stride(d)
      indices(d) += 1
      if (indices(d) >= shape(d) && d > 0) {
        while (indices(d) >= shape(d) && d > 0) {
          indices(d) = 0
          d -= 1
          indices(d) += 1
          xi += stride(d) - (stride(d + 1) * shape(d + 1))
        }
        d = rank - 1
      }
      yi += 1
    }
    new UntypedDenseTensor.Contiguous[Float](y, shape)
  }

  def zipWith(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float])(f: (Float, Float) => Float): UntypedDenseTensor[Float] = {
    if (x1.rank == 0 && x2.rank == 0) return scalar(f(x1(), x2()))
    val y = new Array[Float](x1.size)
    var yi = 0
    var x1i = x1.offset
    var x2i = x2.offset
    var d = x1.rank - 1
    val indices = Array.fill(x1.rank)(0)
    while (yi < x1.size) {
      y(yi) = f(x1.handle(x1i), x2.handle(x2i))
      x1i += x1.stride(d)
      x2i += x2.stride(d)
      indices(d) += 1
      if (indices(d) >= x1.shape(d) && d > 0) {
        while (indices(d) >= x1.shape(d) && d > 0) {
          indices(d) = 0
          d -= 1
          indices(d) += 1
          x1i += x1.stride(d) - (x1.stride(d + 1) * x1.shape(d + 1))
          x2i += x2.stride(d) - (x2.stride(d + 1) * x2.shape(d + 1))
        }
        d = x1.rank - 1
      }
      yi += 1
    }
    new UntypedDenseTensor.Contiguous[Float](y, x1.shape)
  }

  def inplace2(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float])(f: (Float, Float) => Float): Unit = {
    if (x1.rank == 0 && x2.rank == 0) {
      x1.update()(f(x1(), x2()))
      return
    }
    var yi = 0
    var x1i = x1.offset
    var x2i = x2.offset
    var d = x1.rank - 1
    val indices = Array.fill(x1.rank)(0)
    while (yi < x1.size) {
      x1.handle(x1i) = f(x1.handle(x1i), x2.handle(x2i))
      x1i += x1.stride(d)
      x2i += x2.stride(d)
      indices(d) += 1
      if (indices(d) >= x1.shape(d) && d > 0) {
        while (indices(d) >= x1.shape(d) && d > 0) {
          indices(d) = 0
          d -= 1
          indices(d) += 1
          x1i += x1.stride(d) - (x1.stride(d + 1) * x1.shape(d + 1))
          x2i += x2.stride(d) - (x2.stride(d + 1) * x2.shape(d + 1))
        }
        d = x1.rank - 1
      }
      yi += 1
    }
  }


  def negS(x: Float) = -x
  def negU(x: UntypedDenseTensor[Float]) = map(x)(-_)
  def addU(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float]) = zipWith(x1, x2)(_+_)
  def subU(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float]) = zipWith(x1, x2)(_-_)
  def mulU(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float]) = zipWith(x1, x2)(_*_)
  def divU(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float]) = zipWith(x1, x2)(_/_)

  def inv(x: UntypedDenseTensor[Float]) = map(x)(1f/_)


  def scaleU(x: UntypedDenseTensor[Float], u: Float) = map(x)(_ * u)

  def addInplace(x: UntypedDenseTensor[Float], d: UntypedDenseTensor[Float]) = inplace2(x, d)(_+_)

  def logU(x: UntypedDenseTensor[Float]) = map(x)(x => math.log(x).toFloat)
  def getScalar(x: UntypedDenseTensor[Float]) = x.handle(0)
  def scalar(x: Float) = DenseTensor.fill(x, HNil, Array())
  def addScalarU(x: UntypedDenseTensor[Float], u: Float) = map(x)(a => a + u)

  def transposeU(x: UntypedDenseTensor[Float]) = new UntypedDenseTensor.View[Float](
    handle = x.handle,
    shape = Array(x.shape(1), x.shape(0)),
    offset = 0,
    stride = Array(x.stride(1), x.stride(0))
  )

  def mvMulU(x: UntypedDenseTensor[Float], y: UntypedDenseTensor[Float]) = {
    require(x.rank == 2 && y.rank == 1)
    val z = Array.fill(x.shape(0))(0f)
    for (i <- 0 until x.shape(0))
      for (j <- 0 until x.shape(1))
        z(i) += x(i, j) * y(j)
    new UntypedDenseTensor.Contiguous[Float](z, Array(x.shape(0)))
  }

  def vvMulU(x: UntypedDenseTensor[Float], y: UntypedDenseTensor[Float]) = {
    require(x.rank == 1 && y.rank == 1)
    val z = Array.fill(x.shape(0) * y.shape(0))(0f)
    for (i <- 0 until x.shape(0))
      for (j <- 0 until y.shape(0))
        z(i * y.shape(0) + j) = x(i) * y(j)
    new UntypedDenseTensor.Contiguous[Float](z, Array(x.shape(0), y.shape(0)))
  }

  def sigmoidU(x: UntypedDenseTensor[Float]) = map(x)(a => 1f / (1f + math.exp(-a).toFloat))
  def reluU(x: UntypedDenseTensor[Float]) = map(x)(a => if (a >= 0) a else 0f)

  def reduceSumU(x: UntypedDenseTensor[Float]) = scalar(x.handle.sum)
}

