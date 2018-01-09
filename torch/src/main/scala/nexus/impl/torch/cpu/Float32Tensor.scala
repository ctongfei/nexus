package nexus.impl.torch.cpu

import nexus._
import jtorch.cpu._
import nexus.algebra._

/**
 * 32-bit floating point dense tensor, implemented in Torch.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Float32Tensor[A](val handle: THFloatTensor) {

  import THFloatTensorSyntax._

  def rank = handle.rank

  def shape = handle.shape

  def apply(indices: Int*) = indices.length match {
    case 0 => ???
    case 1 => TH.THFloatTensor_get1d(handle, indices(0))
    case 2 => TH.THFloatTensor_get2d(handle, indices(0), indices(1))
    case 3 => TH.THFloatTensor_get3d(handle, indices(0), indices(1), indices(2))
    case 4 => TH.THFloatTensor_get4d(handle, indices(0), indices(1), indices(2), indices(3))
    case _ => ???
  }

  def stringPrefix = "TorchCPUFloatTensor"

  def stringBody = handle.stringRepr

  override def finalize() = {
    TH.THFloatTensor_free(handle)
    super.finalize()
  }

  override def toString = stringPrefix + "\n" + stringBody

}

object Float32Tensor extends IsRealTensorH[Float32Tensor, Float] {

  type H = THFloatTensor

  object H extends IsUntypedRealTensor[THFloatTensor, Float] {

    val R = Float32

    def copy(a: THFloatTensor) = TH.THFloatTensor_newClone(a)

    def zeroBy(x: THFloatTensor) =
      TH.THFloatTensor_newWithTensor(x)

    def add(a: THFloatTensor, b: THFloatTensor) = {
      val c = copy(a)
      TH.THFloatTensor_add(c, b, 1f)
      c
    }

    def addI(a: THFloatTensor, b: THFloatTensor): Unit =
      TH.THFloatTensor_add(a, b, 1f)

    def addS(x1: THFloatTensor, x2: Float) = {
      val c = copy(x1)
      ???
    }

    def sub(a: THFloatTensor, b: THFloatTensor) = {
      val c = copy(a)
      TH.THFloatTensor_add(c, b, -1f)
      c
    }

    def neg(x: THFloatTensor) = ???
    def eMul(x1: THFloatTensor, x2: THFloatTensor) = ???
    def eDiv(x1: THFloatTensor, x2: THFloatTensor) = ???
    def scale(x: THFloatTensor, k: Float32) = ???
    def eInv(x: THFloatTensor) = ???
    def eSqr(x: THFloatTensor) = ???
    def eSqrt(x: THFloatTensor) = ???
    def log(x: THFloatTensor) = ???
    def exp(x: THFloatTensor) = ???
    def log1p(x: THFloatTensor) = ???
    def expm1(x: THFloatTensor) = ???
    def sin(x: THFloatTensor) = ???
    def cos(x: THFloatTensor) = ???
    def tan(x: THFloatTensor) = ???
    def sum(x: THFloatTensor) = ???
    def sigmoid(x: THFloatTensor) = ???
    def reLU(x: THFloatTensor) = ???
    def abs(x: THFloatTensor) = ???
    def sgn(x: THFloatTensor) = ???
    def isPos(x: THFloatTensor) = ???
    def transpose(x: THFloatTensor) = ???
    def mmMul(x: THFloatTensor, y: THFloatTensor) = ???
    def mvMul(x: THFloatTensor, y: THFloatTensor) = ???
    def vvMul(x: THFloatTensor, y: THFloatTensor) = ???
    def dot(x: THFloatTensor, y: THFloatTensor) = ???
    def tMul(x: THFloatTensor, y: THFloatTensor, matchedIndices: Seq[(Int32, Int32)]) = ???
    def mutable = ???
    def addS(x1: THFloatTensor, x2: Float64) = ???
    def fromFlatArray(array: Array[Float32], shape: Array[Int32]) = ???
    def unwrapScalar(x: THFloatTensor) = ???
    def wrapScalar(x: Float32) = ???
    def map(x: THFloatTensor)(f: Float32 => Float32) = ???
    def map2(x1: THFloatTensor, x2: THFloatTensor)(f: (Float32, Float32) => Float32) = ???
    def map3(x1: THFloatTensor, x2: THFloatTensor, x3: THFloatTensor)(f: (Float32, Float32, Float32) => Float32) = ???
    def expandDim(x: THFloatTensor, i: Int32) = ???
  }



  def newDim0Tensor(x: Float): Float32Tensor[$] = ???

  def fill[A](value: => Float, axes: A, shape: Array[Int]) = ???


  implicit val R: IsReal[Float] = nexus.algebra.instances.Float32

  def untype(x: Float32Tensor[_]) = x.handle
  def typeWith[A](x: THFloatTensor) = new Float32Tensor[A](x)

  def zeroBy[A](x: Float32Tensor[A]) = ???
  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]) = ???
  def newTensor[A](shape: Array[Int]) = ???

}
