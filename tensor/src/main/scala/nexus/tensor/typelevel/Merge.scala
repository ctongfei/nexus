package nexus.tensor.typelevel

import shapeless._

/**
 * Typelevel function that merges two adjacent elements into one.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Merge[A, U, V, W] {

  type Out
  type Index <: Nat
  def index: Int

  def inverse: Split.Aux[Out, W, U, V, A]

}

object Merge {

  def apply[A, U, V, W, B](implicit m: Merge.Aux[A, U, V, W, B]) = m
  type Aux[A, U, V, W, B] = Merge[A, U, V, W] { type Out = B }

  implicit def mergeHListCase0[T <: HList, U, V, W]: Aux[U :: V :: T, U, V, W, W :: T] =
    new Merge[U :: V :: T, U, V, W] { m =>
      type Out = W :: T
      type Index = _0
      def index = 0
      def inverse = Split.splitHListCase0
    }

  implicit def mergeHListCaseN[T <: HList, H, U, V, W, R <: HList]
  (implicit m: Merge.Aux[T, U, V, W, R]): Merge.Aux[H :: T, U, V, W, H :: R] =
    new Merge[H :: T, U, V, W] {
      type Out = H :: R
      type Index = Succ[m.Index]
      def index = m.index + 1
      def inverse = Split.splitHListCaseN(m.inverse)
    }

  implicit def mergeTuple[A, Al <: HList, U, V, W, Bl <: HList, B]
  (implicit al: ToHList.Aux[A, Al], m: Merge.Aux[Al, U, V, W, Bl], bl: FromHList.Aux[Bl, B]): Merge.Aux[A, U, V, W, B] =
    new Merge[A, U, V, W] {
      type Out = B
      type Index = m.Index
      def index = m.index
      def inverse = Split.splitTuple(bl.inverse, m.inverse, al.inverse)
    }

}
