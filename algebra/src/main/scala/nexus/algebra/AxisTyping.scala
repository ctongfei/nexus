package nexus.algebra

import nexus._

/**
 * @author Tongfei Chen
 */
trait AxisTyping[T[_ <: $$]] {

  type H

  def untype(x: T[_]): H

  def typeOf[A <: $$](x: T[A]): A

  def typeWith[A <: $$](x: H, a: A): T[A]

}
