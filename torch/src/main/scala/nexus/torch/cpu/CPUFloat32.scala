package nexus.torch.cpu

import nexus._
import nexus.torch._
import jtorch._
import nexus.impl._

/**
 * Runtime environment for Float32 tensors that is backed by Torch.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object CPUFloat32 extends TypedMathOps[FloatDenseTensor, Float] with Typing[FloatDenseTensor] {
  type H = THFloatTensor
  def untype(x: FloatDenseTensor[_]) = ???
  def typeOf[A <: $$](x: FloatDenseTensor[A]) = ???
  def typeWith[A <: $$](x: THFloatTensor, a: A) = ???
  def H = ???
  def D = ???
  def newTensor[A <: $$](axes: A, shape: Array[Int]) = ???
  def newGaussianTensor[A <: $$](μ: Double, σ2: Double, axes: A, shape: Array[Int]) = ???

  def newZeroBy[A <: $$](x: FloatDenseTensor[A]) = ???
  def fromDouble(d: Double) = ???
  def fromFloat(f: Float) = ???
}
