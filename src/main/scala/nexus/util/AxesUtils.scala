package nexus.util

import shapeless._

/**
 * @author Tongfei Chen
 */
object AxesUtils {

  def swap[A, B](axes: A :: B :: HNil): B :: A :: HNil =
    axes.apply(1).asInstanceOf[B] :: axes.apply(0).asInstanceOf[A] :: HNil

}
