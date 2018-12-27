package nexus.typelevel

import shapeless._

/**
 * Typelevel function that witnesses type [[U]] is not a member of [[A]].
 * @author Tongfei Chen
 */
trait NotContains[A, U]

object NotContains {

  def apply[A, U](implicit n: NotContains[A, U]) = n

  implicit def case0[U]: NotContains[HNil, U]
    = new NotContains[HNil, U] {}

  implicit def case1[Ah, At <: HList, U]
  (implicit t: NotContains[At, U], n: Ah =:!= U): NotContains[Ah :: At, U]
    = new NotContains[Ah :: At, U] {}

  implicit def tuple[A, Al <: HList, U]
  (implicit ah: ToHList.Aux[A, Al], n: NotContains[Al, U]): NotContains[A, U]
    = new NotContains[A, U] {}

}
