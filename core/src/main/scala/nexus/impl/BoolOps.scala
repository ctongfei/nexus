package nexus.impl

import algebra.lattice._

/**
 * Encapsulates mathematical operations on booleans.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait BoolOps[@specialized(Boolean) B] extends Bool[B] {

  def top: B
  def one = top

  def bot: B
  def zero = bot

  def not(a: B): B
  def complement(a: B) = not(a)

}

object BoolOps {

  implicit object Bool extends BoolOps[Boolean] {
    def top = true
    def bot = false
    def not(a: Boolean) = !a
    def and(a: Boolean, b: Boolean) = a && b
    def or(a: Boolean, b: Boolean) = a || b
    override def xor(a: Boolean, b: Boolean) = a ^ b
  }

}
