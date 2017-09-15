package nexus.algebra.instances

import nexus.algebra._

object Bool extends IsBool[Boolean] {
  def top = true
  def bot = false
  def not(a: Boolean) = !a
  def and(a: Boolean, b: Boolean) = a && b
  def or(a: Boolean, b: Boolean) = a || b
  override def xor(a: Boolean, b: Boolean) = a ^ b
}
