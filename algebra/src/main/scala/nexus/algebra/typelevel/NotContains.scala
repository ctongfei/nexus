package nexus.algebra.typelevel

import nexus.algebra._
import shapeless._

/**
 * Typelevel function that witnesses type [[X]] is not a member of [[L]].
 * @author Tongfei Chen
 */
trait NotContains[L, X]

object NotContains {

  def apply[L, X](implicit n: NotContains[L, X]) = n

  implicit def notContainsHListCase0[X]: NotContains[HNil, X]
    = new NotContains[HNil, X] {}

  implicit def notContainsHListCaseN[H, T <: HList, X]
  (implicit t: NotContains[T, X], n: H =:!= X): NotContains[H :: T, X]
    = new NotContains[H :: T, X] {}

  implicit def notContainsTuple[A, Ah <: HList, X]
  (implicit ah: ToHList.Aux[A, Ah], n: NotContains[Ah, X]): NotContains[A, X]
    = new NotContains[A, X] {}

}
