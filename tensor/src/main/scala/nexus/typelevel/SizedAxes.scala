package nexus.typelevel

import nexus._
import shapeless._

/**
 * @author Tongfei Chen
 */
trait SizedAxes[A] {
  type Out
  def shape(a: A): List[Int]
}

object SizedAxes {

  def apply[A, B](implicit s: SizedAxes.Aux[A, B]) = s
  type Aux[A, B] = SizedAxes[A] { type Out = B }

  implicit def case0: Aux[HNil, HNil] = new SizedAxes[HNil] {
    type Out = HNil
    def shape(a: HNil) = Nil
  }

  implicit def caseN[Ah, At <: HList, Bt <: HList](implicit s: SizedAxes.Aux[At, Bt]): Aux[(Ah, Int) :: At, Ah :: Bt] =
    new SizedAxes[(Ah, Int) :: At] {
      type Out = Ah :: Bt
      def shape(a: (Ah, Int) :: At) = a.head._2 :: s.shape(a.tail)
    }

  implicit def tuple[A, Al <: HList, Bl <: HList, B]
  (implicit al: ToHList.Aux[A, Al], s: SizedAxes.Aux[Al, Bl], bl: FromHList.Aux[Bl, B]): Aux[A, B] =
    new SizedAxes[A] {
      type Out = B
      def shape(a: A) = s.shape(al(a))
    }
}
