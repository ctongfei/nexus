package nexus.cpu

import nexus._
import shapeless.HList

/**
 * @author Tongfei Chen
 */
class CPUFloat32 extends Env[Tensor, Float] {

  def neg[A <: HList](x: Tensor[Float, A]) = ???
  def add[A <: HList](x: Tensor[Float, A], y: Tensor[Float, A]) = ???
  def sub[A <: HList](x: Tensor[Float, A], y: Tensor[Float, A]) = ???
  def mul[A <: HList](x: Tensor[Float, A], y: Tensor[Float, A]) = ???
  def div[A <: HList](x: Tensor[Float, A], y: Tensor[Float, A]) = ???

}
