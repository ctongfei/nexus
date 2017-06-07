package nexus

import shapeless._

/**
 * Typelevel function that gets the index of type [[X]] in [[L]].
 */
trait IndexOf[L <: HList, X] extends DepFn0 { type Out <: Nat }

object IndexOf {

  def apply[L <: HList, X](implicit o: IndexOf[L, X]): Aux[L, X, o.Out] = o
  type Aux[L <: HList, X, Out0 <: Nat] = IndexOf[L, X] { type Out = Out0 }

  implicit def indexOf0[T <: HList, X]: Aux[X :: T, X, _0] =
    new IndexOf[X :: T, X] {
        type Out = _0
        def apply() = Nat._0
      }

  implicit def indexOfN[T <: HList, H, X, I <: Nat]
  (implicit indexOfP: IndexOf.Aux[T, X, I]): Aux[H :: T, X, Succ[I]] =
    new IndexOf[H :: T, X] {
        type Out = Succ[I]
        def apply() = Succ[I]
    }

}

/**
 * Typelevel function that removes the [[I]]th type in [[L]].
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

/**
 * Typelevel function that inserts type [[X]] at the [[I]]-th position in [[L]].
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
