package nexus.cpu

import nexus._
import shapeless._

/**
 * @author Tongfei Chen
 */
class CPUFloat32 extends Env[UntypedDenseTensor, Float] {

  def zero = 0f
  def one = 1f

  def map(x: UntypedDenseTensor[Float])(f: Float => Float): UntypedDenseTensor[Float] = {
    import x._
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

  def neg(x: UntypedDenseTensor[Float]) = map(x)(-_)
  def add(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float]) = zipWith(x1, x2)(_+_)
  def sub(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float]) = zipWith(x1, x2)(_-_)
  def mul(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float]) = zipWith(x1, x2)(_*_)
  def div(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float]) = zipWith(x1, x2)(_/_)

  def inv(x1: UntypedDenseTensor[Float]) = map(x)(1f/_)

  def addInplace(x: UntypedDenseTensor[Float], d: UntypedDenseTensor[Float]) = inplace2(x, d)(_+_)

  def log(x: UntypedDenseTensor[Float]) = map(x)(x => math.log(x).toFloat)
  def getScalar(x: UntypedDenseTensor[Float]) = x.handle(0)
  def scalar(x: Float) = DenseTensor.fill(x, HNil, Array())
  def addS(x: UntypedDenseTensor[Float], u: Float) = map(x)(a => a + u)

  def transpose(x: UntypedDenseTensor[Float]) = new UntypedDenseTensor.View[Float](
    handle = x.handle,
    shape = Array(x.shape(1), x.shape(0)),
    offset = 0,
    stride = Array(x.stride(1), x.stride(0))
  )

  def mvMul(x: UntypedDenseTensor[Float], y: UntypedDenseTensor[Float]) = {
    require(x.rank == 2 && y.rank == 1)
    val z = Array.fill(x.shape(0))(0f)
    for (i <- 0 until x.shape(0))
      for (j <- 0 until x.shape(1))
        z(i) += x(i, j) * y(j)
    new UntypedDenseTensor.Contiguous[Float](z, Array(x.shape(0)))
  }

  def vvMul(x: UntypedDenseTensor[Float], y: UntypedDenseTensor[Float]) = {
    require(x.rank == 1 && y.rank == 1)
    val z = Array.fill(x.shape(0) * y.shape(0))(0f)
    for (i <- 0 until x.shape(0))
      for (j <- 0 until y.shape(0))
        z(i * y.shape(0) + j) = x(i) * y(j)
    new UntypedDenseTensor.Contiguous[Float](z, Array(x.shape(0), y.shape(0)))
  }

  def sigmoid(x: UntypedDenseTensor[Float]) = map(x)(a => 1f / (1f + math.exp(-a).toFloat))
}

