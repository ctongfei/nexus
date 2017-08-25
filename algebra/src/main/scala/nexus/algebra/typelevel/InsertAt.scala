package nexus.algebra.typelevel

import shapeless._

/**
 * Typelevel function that inserts type [[X]] at the [[I]]-th position in [[L]].
 * @author Tongfei Chen
 */
trait InsertAt[L <: HList, I <: Nat, X] extends DepFn2[L, X] { type Out <: HList }

object InsertAt {

  def apply[L <: HList, I <: Nat, X](implicit o: InsertAt[L, I, X]): Aux[L, I, X, o.Out] = o
  type Aux[L <: HList, I <: Nat, X, Out0 <: HList] = InsertAt[L, I, X] { type Out = Out0 }

  implicit def insertAt0[T <: HList, H]: Aux[T, _0, H, H :: T] =
    new InsertAt[T, _0, H] {
      type Out = H :: T
      def apply(t: T, h: H): H :: T = h :: t
    }

  implicit def insertAtN[H, T <: HList, P <: Nat, X, R <: HList](implicit ev: InsertAt.Aux[T, P, X, R]): Aux[H :: T, Succ[P], X, H :: R] =
    new InsertAt[H :: T, Succ[P], X] {
      type Out = H :: R
      def apply(t: H :: T, x: X): H :: R = t.head :: ev(t.tail, x)
    }

}
