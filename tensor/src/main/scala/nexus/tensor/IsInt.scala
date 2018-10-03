package nexus.tensor

import cats._
import scala.annotation._

/**
 * Encapsulates mathematical operations on integers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that type ${Z} is an integer.")
trait IsInt[@specialized(Byte, Short, Int, Long) Z] extends Type[Z] {

  def zero: Z
  def one: Z
  def two: Z
  def negOne: Z

  def add(x: Z, y: Z): Z
  def sub(x: Z, y: Z): Z
  def neg(x: Z): Z
  def mul(x: Z, y: Z): Z
  def div(x: Z, y: Z): Z
  def mod(x: Z, y: Z): Z

}
