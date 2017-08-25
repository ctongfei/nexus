package nexus.algebra.typelevel

import shapeless._

/**
 * Typelevel function that removes the [[I]]th type in [[L]].
 * @author Tongfei Chen
 */
trait RemoveAt[L <: HList, I <: Nat] extends DepFn1[L] { type Out <: HList }

object RemoveAt {

  def apply[L <: HList, I <: Nat](implicit o: RemoveAt[L, I]): Aux[L, I, o.Out] = o
  type Aux[L <: HList, I <: Nat, Out0 <: HList] = RemoveAt[L, I] { type Out = Out0 }

  implicit def removeAt0[T <: HList, H]: Aux[H :: T, _0, T] =
    new RemoveAt[H :: T, _0] {
      type Out = T
      def apply(t: H :: T): T = t.tail
    }

  implicit def removeAtN[T <: HList, H, R <: HList, P <: Nat]
  (implicit ev: RemoveAt.Aux[T, P, R]): Aux[H :: T, Succ[P], H :: R] =
    new RemoveAt[H :: T, Succ[P]] {
      type Out = H :: R
      def apply(t: H :: T): H :: R = t.head :: ev(t.tail)
    }

}
