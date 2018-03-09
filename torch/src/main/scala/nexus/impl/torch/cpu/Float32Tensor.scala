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

object Float32Tensor extends IsRealTensorK[Float32Tensor, Float] {

  type H = THFloatTensor

  val H = UntypedFloat32Tensor

  def newDim0Tensor(x: Float): Float32Tensor[Unit] = ???

  def fill[A](value: => Float, axes: A, shape: Array[Int]) = ???


  implicit val R: IsReal[Float] = nexus.algebra.instances.Float32

  def untype(x: Float32Tensor[_]) = x.handle
  def typeWith[A](x: THFloatTensor) = new Float32Tensor[A](x)

  def zeroBy[A](x: Float32Tensor[A]) = ???
  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]) = ???
  def newTensor[A](shape: Array[Int]) = ???

}
