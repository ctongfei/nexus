package nexus.algebra.typelevel

import shapeless._

/**
 * Typelevel function that gets the index of the first occurrence of type [[X]] in [[L]].
 * @author Tongfei Chen
 */
trait IndexOf[L <: HList, X] extends DepFn0 {
  type Out <: Nat
  def toInt: Int
}

object IndexOf {

  def apply[L <: HList, X](implicit o: IndexOf[L, X]): Aux[L, X, o.Out] = o
  type Aux[L <: HList, X, Out0 <: Nat] = IndexOf[L, X] { type Out = Out0 }

  implicit def indexOf0[T <: HList, X]: Aux[X :: T, X, _0] =
    new IndexOf[X :: T, X] {
        type Out = _0
        def apply() = Nat._0
        def toInt = 0
    }

  implicit def indexOfN[T <: HList, H, X, I <: Nat]
  (implicit p: IndexOf.Aux[T, X, I]): Aux[H :: T, X, Succ[I]] =
    new IndexOf[H :: T, X] {
        type Out = Succ[I]
        def apply() = Succ[I]
        def toInt = p.toInt + 1
    }

}
