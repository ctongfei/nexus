package nexus.instances

import nexus._

object BoolIsBool extends IsBool[Boolean] {
  def top = true
  def bot = false
  def not(a: Boolean) = !a
  def and(a: Boolean, b: Boolean) = a && b
  def or(a: Boolean, b: Boolean) = a || b
  override def xor(a: Boolean, b: Boolean) = a ^ b
}

object BoolIdCond extends Cond[Boolean, Id] {
  def cond[A](c: Boolean, t: A, f: A) = if (c) t else f
}

object BoolFunction0Cond extends Cond[Boolean, Function0] {
  def cond[A](c: Boolean, t: () => A, f: () => A) = if (c) t else f
}
