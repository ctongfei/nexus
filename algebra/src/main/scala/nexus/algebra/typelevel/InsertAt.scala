package nexus.algebra.typelevel

import shapeless._

/**
 * Typelevel function that inserts type [[X]] at the [[I]]-th position in [[L]].
 * @author Tongfei Chen
 */
trait InsertAt[L, I <: Nat, X] extends DepFn2[L, X] {
  type Out
}

object InsertAt {

  def apply[L, I <: Nat, X](implicit o: InsertAt[L, I, X]): Aux[L, I, X, o.Out] = o
  type Aux[L, I <: Nat, X, Out0] = InsertAt[L, I, X] { type Out = Out0 }

  implicit def insertAtHListCase0[T <: HList, H]: Aux[T, _0, H, H :: T] =
    new InsertAt[T, _0, H] {
      type Out = H :: T
      def apply(t: T, h: H): H :: T = h :: t
    }

  implicit def insertAtHListCaseN[H, T <: HList, P <: Nat, X, R <: HList](implicit ev: InsertAt.Aux[T, P, X, R]): Aux[H :: T, Succ[P], X, H :: R] =
    new InsertAt[H :: T, Succ[P], X] {
      type Out = H :: R
      def apply(t: H :: T, x: X): H :: R = t.head :: ev(t.tail, x)
    }

  implicit def insertAtTuple[L, Lh <: HList, I <: Nat, X, Rh <: HList, R]
  (implicit l: ToHList.Aux[L, Lh], h: InsertAt.Aux[Lh, I, X, Rh], r: ToHList.Aux[R, Rh]): Aux[L, I, X, R] =
    new InsertAt[L, I, X] {
      type Out = R
      def apply(t: L, x: X): R = r.invert(h(l(t), x))
    }
}
