package nexus.impl.torch.cpu

import nexus._
import nexus.impl.torch._
import jtorch._
import nexus.algebra._

/**
 * Runtime environment for Float32 tensors that is backed by Torch.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object CPUFloat32 extends IsTypedRealTensor[FloatDenseTensor, Float] with AxisTyping[FloatDenseTensor] {
  type H = THFloatTensor
  val R = IsReal.Float32
  def untype(x: FloatDenseTensor[_]) = ???
  def typeOf[A <: $$](x: FloatDenseTensor[A]) = ???
  def typeWith[A <: $$](x: THFloatTensor, a: A) = ???
  val H = ???
  def newTensor[A <: $$](axes: A, shape: Array[Int]) = ???
  def newGaussianTensor[A <: $$](μ: Double, σ2: Double, axes: A, shape: Array[Int]) = ???

  def zeroBy[A <: $$](x: FloatDenseTensor[A]) = ???
  def fromDouble(d: Double) = ???
  def fromFloat(f: Float) = ???
}
