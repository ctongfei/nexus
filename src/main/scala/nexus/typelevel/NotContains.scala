package nexus.typelevel

import shapeless._

/**
 * Typelevel function that witnesses type [[X]] is not a member of [[L]].
 * @author Tongfei Chen
 */
trait NotContains[L <: HList, X]

object NotContains {

  def apply[L <: HList, X](implicit n: NotContains[L, X]) = n

  implicit def case0[X]: NotContains[HNil, X]
    = new NotContains[HNil, X] {}

  implicit def case1[H, T <: HList, X](implicit t: NotContains[T, X], n: H =:!= X): NotContains[H :: T, X]
    = new NotContains[H :: T, X] {}

}
