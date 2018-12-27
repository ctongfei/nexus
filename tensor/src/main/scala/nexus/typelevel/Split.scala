package nexus.typelevel

import shapeless._

/**
 * Typelevel function that splits an element into two adjacent elements.
 * @author Tongfei Chen
 */
trait Split[A, U, V, W] {
  type Out
  type Index <: Nat
  def index: Int

  def inverse: Merge.Aux[Out, V, W, U, A]

}

object Split {

  def apply[A, U, V, W, B](implicit s: Split.Aux[A, U, V, W, B]) = s
  type Aux[A, U, V, W, B] = Split[A, U, V, W] { type Out = B }

  implicit def case0[T <: HList, U, V, W]: Aux[U :: T, U, V, W, V :: W :: T] =
    new Split[U :: T, U, V, W] { s =>
      type Out = V :: W :: T
      type Index = _0
      def index = 0
      def inverse = Merge.case0
    }

  implicit def caseN[T <: HList, H, U, V, W, R <: HList]
  (implicit s: Split.Aux[T, U, V, W, R]): Aux[H :: T, U, V, W, H :: R] =
    new Split[H :: T, U, V, W] {
      type Out = H :: R
      type Index = Succ[s.Index]
      def index = s.index + 1
      def inverse = Merge.caseN(s.inverse)
    }

  implicit def tuple[A, Al <: HList, U, V, W, Bl <: HList, B]
  (implicit al: ToHList.Aux[A, Al], s: Split.Aux[Al, U, V, W, Bl], bl: FromHList.Aux[Bl, B]): Aux[A, U, V, W, B] =
    new Split[A, U, V, W] {
      type Out = B
      type Index = s.Index
      def index = s.index
      def inverse = Merge.tuple(bl.inverse, s.inverse, al.inverse)
    }

}
