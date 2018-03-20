package nexus.impl.torch.cpu

import nexus._
import jtorch.cpu._
import nexus.algebra._

/**
 * 32-bit floating point dense tensor, implemented in Torch.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Float32Tensor[A](val untyped: UntypedFloatTensor) {

  import THFloatTensorSyntax._
  import UntypedFloatTensor._
  import Float32Tensor._

  def rank = Float32Tensor.rank(this)

  def shape = Float32Tensor.shape(this)

  def apply(indices: Int*) = untyped match {
    case Dim0(x) if indices.length == 0 => x
    case Dense(x) =>
      indices.length match {
        case 0 => ???
        case 1 => TH.THFloatTensor_get1d(x, indices(0))
        case 2 => TH.THFloatTensor_get2d(x, indices(0), indices(1))
        case 3 => TH.THFloatTensor_get3d(x, indices(0), indices(1), indices(2))
        case 4 => TH.THFloatTensor_get4d(x, indices(0), indices(1), indices(2), indices(3))
        case _ => ???
      }
  }

}

object Float32Tensor extends IsRealTensorK[Float32Tensor, Float] {

  type H = UntypedFloatTensor
  val H = UntypedFloatTensor

  implicit val R = Float32

  def get(x: Float32Tensor[_], is: Array[Int32]) = ???
  def set(x: Float32Tensor[_], is: Array[Int32], v: Float32): Unit = ???

  def newDim0Tensor(x: Float): Float32Tensor[Unit] = ???

  def fill[A](value: => Float, axes: A, shape: Array[Int]) = ???


  def untype(x: Float32Tensor[_]) = x.untyped
  def typeWith[A](x: UntypedFloatTensor) = new Float32Tensor[A](x)

  def zeroBy[A](x: Float32Tensor[A]) = ???
  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]) = ???
  def newTensor[A](shape: Array[Int]) = ???

}
