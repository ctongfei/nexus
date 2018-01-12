package nexus.algebra.typelevel.util

import nexus.algebra._
import shapeless._

/**
 * Contains routines that manipulates axis HLists.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object AxesUtils {

  def swap[A, B](axes: A::B::$): B::A::$ =
    axes.tail.head :: axes.head :: $

  def mmMul[A, B, C](lhs: A::B::$, rhs: B::C::$): A::C::$ =
    lhs.head :: rhs.tail.head :: $

}
