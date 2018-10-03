package nexus.tensor.typelevel

import shapeless._

/**
 * Typelevel function that removes a specific element from a list.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Remove[A, X] extends DepFn1[A] {
  type Out
  def index: Int
}

object Remove {

  def apply[A, X, B](implicit o: Remove.Aux[A, X, B]) = o
  type Aux[A, X, B] = Remove[A, X] { type Out = B }

  implicit def removeHListCase0[T <: HList, H]: Aux[H :: T, H, T] =
    new Remove[H :: T, H] {
      type Out = T
      def index = 0
      def apply(t: H :: T): T = t.tail
    }

  implicit def removeHListCaseN[T <: HList, H, X, R <: HList]
  (implicit r: Remove.Aux[T, X, R]): Aux[H :: T, X, H :: R] =
    new Remove[H :: T, X] {
      type Out = H :: R
      def index = r.index + 1
      def apply(t: H :: T): H :: R = t.head :: r(t.tail)
    }

  implicit def removeTuple[L, Lh <: HList, X, Rh <: HList, R]
  (implicit lh: ToHList.Aux[L, Lh], r: Remove.Aux[Lh, X, Rh], rh: FromHList.Aux[Rh, R]): Aux[L, X, R] =
    new Remove[L, X] {
      type Out = R
      def index = r.index
      def apply(t: L): R = rh(r(lh(t)))
    }

}
