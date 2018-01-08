package nexus.impl.jvm

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel.util._
import shapeless._

import scala.util._

/**
 * @author Tongfei Chen
 */
object CPUFloat32 extends IsRealTensorH[FloatTensor, Float] {
  type H = UntypedFloatTensor
  val H = UntypedCPUFloat32


  val R = H.R

  def newTensor[A <: HList](shape: Array[Int]) =
    FloatTensor.fromFlatArray[A](Array.ofDim[Float](ShapeUtils.product(shape)), shape)

  def newGaussianTensor[A <: $$](μ: Double, σ2: Double, shape: Array[Int]) = {
    val r = new Random()
    val σ = math.sqrt(σ2)
    FloatTensor.fromFlatArray(Array.fill(ShapeUtils.product(shape))(((r.nextGaussian() - μ) * σ).toFloat), shape)
  }


  def zeroBy[A <: $$](x: FloatTensor[A]) = newTensor(x.shape)

  def untype(x: FloatTensor[_]) = x
  def typeWith[A <: $$](x: UntypedFloatTensor) = x.typeWith[A]

  def fromDouble(d: Double) = d.toFloat
  def fromFloat(f: Float) = f

}

object UntypedCPUFloat32 extends IsUntypedRealTensor[UntypedFloatTensor, Float] {

  type R = Float
  val R = nexus.algebra.instances.Float32


  def fromFlatArray(array: Array[R], shape: Array[Int]) =
    new UntypedFloatTensor.Contiguous(array, shape)

  def mutable = true

  def map(x: UntypedFloatTensor)(f: Float => Float): UntypedFloatTensor = {
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
    new UntypedFloatTensor.Contiguous(y, shape)
  }

  def map2(x1: UntypedFloatTensor, x2: UntypedFloatTensor)(f: (Float, Float) => Float): UntypedFloatTensor = {
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
    new UntypedFloatTensor.Contiguous(y, x1.shape)
  }


  def map3(x1: UntypedFloatTensor, x2: UntypedFloatTensor, x3: UntypedFloatTensor)(f: (Float, Float, Float) => Float): UntypedFloatTensor = {
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
    new UntypedFloatTensor.Contiguous(y, x1.shape)
  }

  def inplace2(x1: UntypedFloatTensor, x2: UntypedFloatTensor)(f: (Float, Float) => Float): Unit = {
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


  def zeroBy(x: UntypedFloatTensor) = new UntypedFloatTensor.Contiguous(new Array[Float](x.size), x.shape)

  def neg(x: UntypedFloatTensor) = map(x)(-_)
  def add(x1: UntypedFloatTensor, x2: UntypedFloatTensor) = map2(x1, x2)(_+_)
  def sub(x1: UntypedFloatTensor, x2: UntypedFloatTensor) = map2(x1, x2)(_-_)
  def eMul(x1: UntypedFloatTensor, x2: UntypedFloatTensor) = map2(x1, x2)(_*_)
  def eDiv(x1: UntypedFloatTensor, x2: UntypedFloatTensor) = map2(x1, x2)(_/_)


  def addS(x1: UntypedFloatTensor, x2: Double) = map(x1)(_+x2.toFloat)

  def eSqr(x: UntypedFloatTensor) = map(x)(x => x * x)
  def eSqrt(x: UntypedFloatTensor) = map(x)(x => Math.sqrt(x).toFloat)

  def eInv(x: UntypedFloatTensor) = map(x)(1f/_)


  def scale(x: UntypedFloatTensor, u: Float) = map(x)(_ * u)

  def addI(x: UntypedFloatTensor, d: UntypedFloatTensor) = inplace2(x, d)(_+_)


  def unwrapScalar(x: UntypedFloatTensor) = x.handle(0)
  def wrapScalar(x: Float): UntypedFloatTensor = FloatTensor.fill(x, $, Array())

  def addS(x: UntypedFloatTensor, u: Float) = map(x)(a => a + u)

  def transpose(x: UntypedFloatTensor) = new UntypedFloatTensor.View(
    handle = x.handle,
    shape = Array(x.shape(1), x.shape(0)),
    offset = 0,
    stride = Array(x.stride(1), x.stride(0))
  )


  def mmMul(x: UntypedFloatTensor, y: UntypedFloatTensor) = ???

  def mvMul(x: UntypedFloatTensor, y: UntypedFloatTensor) = {
    require(x.rank == 2 && y.rank == 1)
    val z = Array.fill(x.shape(0))(0f)
    for (i <- 0 until x.shape(0))
      for (j <- 0 until x.shape(1))
        z(i) += x(i, j) * y(j)
    new UntypedFloatTensor.Contiguous(z, Array(x.shape(0)))
  }

  def vvMul(x: UntypedFloatTensor, y: UntypedFloatTensor) = {
    require(x.rank == 1 && y.rank == 1)
    val z = Array.fill(x.shape(0) * y.shape(0))(0f)
    for (i <- 0 until x.shape(0))
      for (j <- 0 until y.shape(0))
        z(i * y.shape(0) + j) = x(i) * y(j)
    new UntypedFloatTensor.Contiguous(z, Array(x.shape(0), y.shape(0)))
  }


  def dot(x: UntypedFloatTensor, y: UntypedFloatTensor): R = sum(eMul(x, y))

  def exp(x: UntypedFloatTensor) = map(x)(a => Math.exp(a).toFloat)
  def log(x: UntypedFloatTensor) = map(x)(x => if (x == 0f) 0f else math.log(x).toFloat)
  def log1p(x: UntypedFloatTensor) = map(x)(x => Math.log1p(x).toFloat)
  def expm1(x: UntypedFloatTensor) = map(x)(x => Math.expm1(x).toFloat)

  def sin(x: UntypedFloatTensor) = map(x)(a => Math.sin(a).toFloat)
  def cos(x: UntypedFloatTensor) = map(x)(a => Math.cos(a).toFloat)
  def tan(x: UntypedFloatTensor) = map(x)(a => Math.tan(a).toFloat)


  def abs(x: UntypedFloatTensor) = map(x)(Math.abs)
  def sgn(x: UntypedFloatTensor) = map(x)(Math.signum)
  def sigmoid(x: UntypedFloatTensor) = map(x)(a => 1f / (1f + math.exp(-a).toFloat))
  def reLU(x: UntypedFloatTensor) = map(x)(a => if (a > 0) a else 0f)
  def isPos(x: UntypedFloatTensor) = map(x)(a => if (a > 0) 1f else 0f)

  def sum(x: UntypedFloatTensor) = x.handle.sum  // TODO: wrong!

  def tMul(x: UntypedFloatTensor, y: UntypedFloatTensor, matchedIndices: Seq[(Int, Int)]) = ???

  def expandDim(x: UntypedFloatTensor, i: Int) = ???
}

