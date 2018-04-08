package nexus.algebra

import scala.annotation._
import algebra.lattice._

/**
 * Encapsulates mathematical operations on booleans.
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that type ${B} is a Boolean.")
trait IsBool[@specialized(Boolean) B] extends Bool[B] with Type[B] {

  def top: B
  def one = top

  def bot: B
  def zero = bot

  def not(a: B): B
  def complement(a: B) = not(a)

}
