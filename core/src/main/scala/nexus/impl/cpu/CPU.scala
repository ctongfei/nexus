package nexus.impl.cpu

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel.util._
import shapeless._

import scala.util._

/**
 * @author Tongfei Chen
 */
object TypedCPUFloat32 extends IsTypedRealTensor[DenseTensor, Float] with AxisTyping[DenseTensor] {
  type H = UntypedDenseTensor
  val H = UntypedCPUFloat32


  val R = H.R

  def newTensor[A <: HList](axes: A, shape: Array[Int]) =
    DenseTensor.fromFlatArray(Array.ofDim[Float](ShapeUtils.product(shape)), axes, shape)

  def newGaussianTensor[A <: $$](μ: Double, σ2: Double, axes: A, shape: Array[Int]) = {
    val r = new Random()
    val σ = math.sqrt(σ2)
    DenseTensor.fromFlatArray(Array.fill(ShapeUtils.product(shape))(((r.nextGaussian() - μ) * σ).toFloat), axes, shape)
  }


  def zeroBy[A <: $$](x: DenseTensor[A]) = newTensor(x.axes, x.shape)

  def untype(x: DenseTensor[_]) = x
  def typeOf[A <: HList](x: DenseTensor[A]) = x.axes
  def typeWith[A <: HList](x: UntypedDenseTensor, a: A) = x typeWith a

  def fromDouble(d: Double) = d.toFloat
  def fromFloat(f: Float) = f


}


object UntypedCPUFloat32 extends IsUntypedRealTensor[UntypedDenseTensor, Float] {

  type R = Float
  val R = nexus.algebra.instances.Float32

  def mutable = true

  def map(x: UntypedDenseTensor)(f: Float => Float): UntypedDenseTensor = {
    import x._
    if (x.rank == 0) return wrapScalar(f(x()))
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
    new UntypedDenseTensor.Contiguous(y, shape)
  }

  def map2(x1: UntypedDenseTensor, x2: UntypedDenseTensor)(f: (Float, Float) => Float): UntypedDenseTensor = {
    if (x1.rank == 0 && x2.rank == 0) return wrapScalar(f(x1(), x2()))
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
    new UntypedDenseTensor.Contiguous(y, x1.shape)
  }


  def map3(x1: UntypedDenseTensor, x2: UntypedDenseTensor, x3: UntypedDenseTensor)(f: (Float, Float, Float) => Float): UntypedDenseTensor = {
    if (x1.rank == 0 && x2.rank == 0 && x3.rank == 0) return wrapScalar(f(x1(), x2(), x3()))
    val y = new Array[Float](x1.size)
    var yi = 0
    var x1i = x1.offset
    var x2i = x2.offset
    var x3i = x3.offset
    var d = x1.rank - 1
    val indices = Array.fill(x1.rank)(0)
    while (yi < x1.size) {
      y(yi) = f(x1.handle(x1i), x2.handle(x2i), x3.handle(x3i))
      x1i += x1.stride(d)
      x2i += x2.stride(d)
      x3i += x3.stride(d)
      indices(d) += 1
      if (indices(d) >= x1.shape(d) && d > 0) {
        while (indices(d) >= x1.shape(d) && d > 0) {
          indices(d) = 0
          d -= 1
          indices(d) += 1
          x1i += x1.stride(d) - (x1.stride(d + 1) * x1.shape(d + 1))
          x2i += x2.stride(d) - (x2.stride(d + 1) * x2.shape(d + 1))
          x3i += x3.stride(d) - (x3.stride(d + 1) * x3.shape(d + 1))
        }
        d = x1.rank - 1
      }
      yi += 1
    }
    new UntypedDenseTensor.Contiguous(y, x1.shape)
  }

  def inplace2(x1: UntypedDenseTensor, x2: UntypedDenseTensor)(f: (Float, Float) => Float): Unit = {
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


  def zeroBy(x: UntypedDenseTensor) = new UntypedDenseTensor.Contiguous(new Array[Float](x.size), x.shape)

  def neg(x: UntypedDenseTensor) = map(x)(-_)
  def add(x1: UntypedDenseTensor, x2: UntypedDenseTensor) = map2(x1, x2)(_+_)
  def sub(x1: UntypedDenseTensor, x2: UntypedDenseTensor) = map2(x1, x2)(_-_)
  def eMul(x1: UntypedDenseTensor, x2: UntypedDenseTensor) = map2(x1, x2)(_*_)
  def eDiv(x1: UntypedDenseTensor, x2: UntypedDenseTensor) = map2(x1, x2)(_/_)


  def addS(x1: UntypedDenseTensor, x2: Double) = map(x1)(_+x2.toFloat)

  def eSqr(x: UntypedDenseTensor) = map(x)(x => x * x)
  def eSqrt(x: UntypedDenseTensor) = map(x)(x => Math.sqrt(x).toFloat)

  def eInv(x: UntypedDenseTensor) = map(x)(1f/_)


  def scale(x: UntypedDenseTensor, u: Float) = map(x)(_ * u)

  def addI(x: UntypedDenseTensor, d: UntypedDenseTensor) = inplace2(x, d)(_+_)


  def unwrapScalar(x: UntypedDenseTensor) = x.handle(0)
  def wrapScalar(x: Float): UntypedDenseTensor = DenseTensor.fill(x, $, Array())

  def addS(x: UntypedDenseTensor, u: Float) = map(x)(a => a + u)

  def transpose(x: UntypedDenseTensor) = new UntypedDenseTensor.View(
    handle = x.handle,
    shape = Array(x.shape(1), x.shape(0)),
    offset = 0,
    stride = Array(x.stride(1), x.stride(0))
  )


  def mmMul(x: UntypedDenseTensor, y: UntypedDenseTensor) = ???

  def mvMul(x: UntypedDenseTensor, y: UntypedDenseTensor) = {
    require(x.rank == 2 && y.rank == 1)
    val z = Array.fill(x.shape(0))(0f)
    for (i <- 0 until x.shape(0))
      for (j <- 0 until x.shape(1))
        z(i) += x(i, j) * y(j)
    new UntypedDenseTensor.Contiguous(z, Array(x.shape(0)))
  }

  def vvMul(x: UntypedDenseTensor, y: UntypedDenseTensor) = {
    require(x.rank == 1 && y.rank == 1)
    val z = Array.fill(x.shape(0) * y.shape(0))(0f)
    for (i <- 0 until x.shape(0))
      for (j <- 0 until y.shape(0))
        z(i * y.shape(0) + j) = x(i) * y(j)
    new UntypedDenseTensor.Contiguous(z, Array(x.shape(0), y.shape(0)))
  }


  def dot(x: UntypedDenseTensor, y: UntypedDenseTensor): R = sum(eMul(x, y))

  def exp(x: UntypedDenseTensor) = map(x)(a => Math.exp(a).toFloat)
  def log(x: UntypedDenseTensor) = map(x)(x => if (x == 0f) 0f else math.log(x).toFloat)
  def log1p(x: UntypedDenseTensor) = map(x)(x => Math.log1p(x).toFloat)
  def expm1(x: UntypedDenseTensor) = map(x)(x => Math.expm1(x).toFloat)

  def sin(x: UntypedDenseTensor) = map(x)(a => Math.sin(a).toFloat)
  def cos(x: UntypedDenseTensor) = map(x)(a => Math.cos(a).toFloat)
  def tan(x: UntypedDenseTensor) = map(x)(a => Math.tan(a).toFloat)


  def abs(x: UntypedDenseTensor) = map(x)(Math.abs)
  def sgn(x: UntypedDenseTensor) = map(x)(Math.signum)
  def sigmoid(x: UntypedDenseTensor) = map(x)(a => 1f / (1f + math.exp(-a).toFloat))
  def reLU(x: UntypedDenseTensor) = map(x)(a => if (a > 0) a else 0f)
  def isPos(x: UntypedDenseTensor) = map(x)(a => if (a > 0) 1f else 0f)




  def sum(x: UntypedDenseTensor) = x.handle.sum  // TODO: wrong!

  def tMul(x: UntypedDenseTensor, y: UntypedDenseTensor, matchedIndices: Seq[(Int, Int)]) = ???
}

