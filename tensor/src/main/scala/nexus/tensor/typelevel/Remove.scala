package nexus.tensor.typelevel

import shapeless._

/**
 * Typelevel function that removes a specific element from a list.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Remove[A, U] extends DepFn1[A] { r =>
  type Out
  type Index <: Nat
  def index: Int

  /**
   * Returns an indexed `RemoveAt` evidence.
   */
  def asRemoveAt: RemoveAt.Aux[A, Index, Out]
}

object Remove {

  def apply[A, U, B](implicit o: Remove.Aux[A, U, B]) = o
  type Aux[A, U, B] = Remove[A, U] { type Out = B }

  implicit def case0[T <: HList, H]: Aux[H :: T, H, T] =
    new Remove[H :: T, H] { rx =>
      type Out = T
      type Index = _0
      def index = 0
      def apply(t: H :: T): T = t.tail
      def asRemoveAt: RemoveAt.Aux[H :: T, _0, T] = RemoveAt.case0
    }

  implicit def caseN[T <: HList, H, U, R <: HList]
  (implicit r: Remove.Aux[T, U, R]): Aux[H :: T, U, H :: R] =
    new Remove[H :: T, U] { rx =>
      type Out = H :: R
      type Index = Succ[r.Index]
      def index = r.index + 1
      def apply(t: H :: T): H :: R = t.head :: r(t.tail)
      def asRemoveAt: RemoveAt.Aux[H :: T, Index, H :: R] = RemoveAt.caseN(r.asRemoveAt)
    }

  implicit def tuple[L, Lh <: HList, X, Rh <: HList, R]
  (implicit lh: ToHList.Aux[L, Lh], r: Remove.Aux[Lh, X, Rh], rh: FromHList.Aux[Rh, R]): Aux[L, X, R] =
    new Remove[L, X] {
      type Out = R
      type Index = r.Index
      def index = r.index
      def apply(t: L): R = rh(r(lh(t)))
      def asRemoveAt: RemoveAt.Aux[L, Index, R] = RemoveAt.tuple(lh, r.asRemoveAt, rh)
    }

}
