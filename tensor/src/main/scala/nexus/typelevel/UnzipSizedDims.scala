package nexus.typelevel

import nexus._
import shapeless._

/**
 * @author Tongfei Chen
 */
trait UnzipSizedDims[A] {
  type Out
  def dims(a: A): Out
  def shape(a: A): List[Int]
}

object UnzipSizedDims {

  def apply[A, B](implicit s: UnzipSizedDims.Aux[A, B]) = s
  type Aux[A, B] = UnzipSizedDims[A] { type Out = B }

  implicit def case0: Aux[HNil, HNil] = new UnzipSizedDims[HNil] {
    type Out = HNil
    def dims(a: HNil) = HNil
    def shape(a: HNil) = Nil
  }

  implicit def caseN[Ah, At <: HList, Bt <: HList](implicit s: UnzipSizedDims.Aux[At, Bt]): Aux[(Ah, Int) :: At, Ah :: Bt] =
    new UnzipSizedDims[(Ah, Int) :: At] {
      type Out = Ah :: Bt
      def dims(a: (Ah, Int) :: At) = a.head._1 :: s.dims(a.tail)
      def shape(a: (Ah, Int) :: At) = a.head._2 :: s.shape(a.tail)
    }

  implicit def tuple[A, Al <: HList, Bl <: HList, B]
  (implicit al: ToHList.Aux[A, Al], s: UnzipSizedDims.Aux[Al, Bl], bl: FromHList.Aux[Bl, B]): Aux[A, B] =
    new UnzipSizedDims[A] {
      type Out = B
      def dims(a: A) = bl(s.dims(al(a)))
      def shape(a: A) = s.shape(al(a))
    }
}
