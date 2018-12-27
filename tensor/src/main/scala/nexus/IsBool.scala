package nexus

import algebra.lattice._
import scala.annotation._

/**
 * Encapsulates mathematical operations on booleans.
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that type ${B} is a Boolean.")
trait IsBool[@specialized(Boolean) B] extends Bool[B] {

  def top: B
  def bot: B

  def not(a: B): B
  def and(a: B, b: B): B
  def or(a: B, b: B): B
  def xor(a: B, b: B): B

  // Conforming to algebra.lattice.Bool
  def complement(a: B) = not(a)
  def zero = bot
  def one = top

}
