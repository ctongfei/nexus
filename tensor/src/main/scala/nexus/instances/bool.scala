package nexus.instances

import nexus._

object BoolIsBool extends IsBool[Boolean] with GetBool[Boolean] {
  def top = true
  def bot = false
  def not(a: Boolean) = !a
  def and(a: Boolean, b: Boolean) = a && b
  def or(a: Boolean, b: Boolean) = a || b
  override def xor(a: Boolean, b: Boolean) = a ^ b

  override def fromBoolean(b: Boolean): Boolean = b
  override def toBoolean(a: Boolean): Boolean = a
}

object BoolIdCond extends Cond[Boolean, Id] {
  def cond[A](c: Boolean, t: => A, f: => A) = if (c) t else f
}
