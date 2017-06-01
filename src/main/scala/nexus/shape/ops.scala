package nexus.shape

import shapeless._
import shapeless.Nat._

/**
 * Witnesses the index of type [[X]] in [[L]].
 */
trait IndexOf[X, L <: HList] extends DepFn0 { type Out <: Nat }

object IndexOf {

  def apply[X, L <: HList](implicit indexOf: IndexOf[X, L]): Aux[X, L, indexOf.Out] = indexOf

  type Aux[X, L <: HList, Out0] = IndexOf[X, L] { type Out = Out0 }

  implicit def indexOfHead[X, T <: HList]: Aux[X, X :: T, _0] =
    new IndexOf[X, X :: T] {
        type Out = _0
        def apply() = Nat._0
      }

  implicit def indexOfOthers[X, H, T <: HList, I <: Nat]
  (implicit indexOf: IndexOf.Aux[X, T, I]): Aux[X, H :: T, Succ[I]] =
    new IndexOf[X, H :: T] {
        type Out = Succ[I]
        def apply() = Succ[I]
    }

}

/**
 * Witnesses the HList without the [[I]]-th type in [[L]].
 */
trait RemoveAt[I <: Nat, L <: HList] extends DepFn1[L] { type Out <: HList }

object RemoveAt {

  def apply[I <: Nat, L <: HList](implicit removeAt: RemoveAt[I, L]): Aux[I, L, removeAt.Out] = removeAt

  type Aux[I <: Nat, L <: HList, Out0 <: HList] = RemoveAt[I, L] { type Out = Out0 }

  implicit def removeAt0[H, T <: HList]: Aux[_0, H :: T, T] =
    new RemoveAt[_0, H :: T] {
      type Out = T
      def apply(t: H :: T): T = t.tail
    }

  implicit def removeAtOther[H, T <: HList, R <: HList, P <: Nat]
  (implicit ev: RemoveAt.Aux[P, T, R]): Aux[Succ[P], H :: T, H :: R] =
    new RemoveAt[Succ[P], H :: T] {
      type Out = H :: R
      def apply(t: H :: T): H :: R = t.head :: ev(t.tail)
    }

}
