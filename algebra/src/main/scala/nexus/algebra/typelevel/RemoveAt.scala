package nexus.algebra.typelevel

import shapeless._

/**
 * Typelevel function that removes the [[I]]th type in [[L]].
 * @author Tongfei Chen
 */
trait RemoveAt[L, I <: Nat] extends DepFn1[L] { type Out }

object RemoveAt {

  def apply[L, I <: Nat](implicit o: RemoveAt[L, I]): Aux[L, I, o.Out] = o
  type Aux[L, I <: Nat, Out0] = RemoveAt[L, I] { type Out = Out0 }

  implicit def removeAtHListCase0[T <: HList, H]: Aux[H :: T, _0, T] =
    new RemoveAt[H :: T, _0] {
      type Out = T
      def apply(t: H :: T): T = t.tail
    }

  implicit def removeAtHListCaseN[T <: HList, H, R <: HList, P <: Nat]
  (implicit r: RemoveAt.Aux[T, P, R]): Aux[H :: T, Succ[P], H :: R] =
    new RemoveAt[H :: T, Succ[P]] {
      type Out = H :: R
      def apply(t: H :: T): H :: R = t.head :: r(t.tail)
    }

  implicit def removeAtTuple[L, Lh <: HList, I <: Nat, Rh <: HList, R]
  (implicit lh: ToHList.Aux[L, Lh], r: RemoveAt.Aux[Lh, I, Rh], rh: ToHList.Aux[R, Rh]): Aux[L, I, R] =
    new RemoveAt[L, I] {
      type Out = R
      def apply(t: L): R = rh.inverse(r(lh(t)))
    }

}
