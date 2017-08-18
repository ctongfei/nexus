package nexus.util

import nexus._

/**
 * @author Tongfei Chen
 */
object AxesUtils {

  def swap[A, B](axes: A :: B :: $): B :: A :: $ =
    axes.tail.head :: axes.head :: $

  def mmMul[A, B, C](lhs: A::B::$, rhs: B::C::$): A::C::$ =
    lhs.head :: rhs.tail.head :: $

}
