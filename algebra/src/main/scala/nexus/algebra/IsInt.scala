package nexus.algebra

import scala.annotation._
import algebra.ring._

/**
 * Encapsulates mathematical operations on integers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that type ${Z} is an integer.")
trait IsInt[@specialized(Byte, Short, Int, Long) Z] extends CommutativeRing[Z] with Type[Z] {

  def add(x: Z, y: Z): Z
  override def plus(x: Z, y: Z) = add(x, y)

  def neg(x: Z): Z
  override def negate(x: Z) = neg(x)

  def sub(x: Z, y: Z): Z
  override def minus(x: Z, y: Z) = sub(x, y)

  def mul(x: Z, y: Z): Z
  override def times(x: Z, y: Z) = mul(x, y)

}
