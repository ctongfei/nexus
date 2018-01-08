package nexus.impl.torch.cpu

import nexus._
import jtorch.cpu._
import nexus.algebra._

/**
 * Runtime environment for Float32 tensors that is backed by Torch on CPU.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object CPUFloat32 extends IsRealTensorH[Float32Tensor, Float] {
  type H = THFloatTensor
  val R = nexus.algebra.instances.Float32

  def untype(x: Float32Tensor[_]) = x.handle
  def typeWith[A <: $$](x: THFloatTensor) = new Float32Tensor[A](x)
  val H = UntypedFloat32Tensor
  def newTensor[A <: $$](shape: Array[Int]) = ???
  def newGaussianTensor[A <: $$](μ: Double, σ2: Double, shape: Array[Int]) = ???

  def zeroBy[A <: $$](x: Float32Tensor[A]) = ???


  def fromDouble(d: Double) = d.toFloat
  def fromFloat(f: Float) = f
}
