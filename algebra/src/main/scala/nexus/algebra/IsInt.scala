package nexus.algebra

import scala.annotation._

/**
 * Encapsulates mathematical operations on integers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that type ${I} is an integer.")
trait IsInt[@specialized(Int, Long) I] extends algebra.ring.CommutativeRing[I] with Type[I] {

  def add(x: I, y: I): I
  override def plus(x: I, y: I) = add(x, y)

  def neg(x: I): I
  override def negate(x: I) = neg(x)

  def sub(x: I, y: I): I
  override def minus(x: I, y: I) = sub(x, y)

  def mul(x: I, y: I): I
  override def times(x: I, y: I) = mul(x, y)

}
