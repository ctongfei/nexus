package nexus.tensor.typelevel

import shapeless._

/**
 * Typelevel function that witnesses type [[U]] is not a member of [[A]].
 * @author Tongfei Chen
 */
trait NotContains[A, U]

object NotContains {

  def apply[A, U](implicit n: NotContains[A, U]) = n

  implicit def notContainsHListCase0[U]: NotContains[HNil, U]
    = new NotContains[HNil, U] {}

  implicit def notContainsHListCaseN[Ah, At <: HList, U]
  (implicit t: NotContains[At, U], n: Ah =:!= U): NotContains[Ah :: At, U]
    = new NotContains[Ah :: At, U] {}

  implicit def notContainsTuple[A, Al <: HList, U]
  (implicit ah: ToHList.Aux[A, Al], n: NotContains[Al, U]): NotContains[A, U]
    = new NotContains[A, U] {}

}
