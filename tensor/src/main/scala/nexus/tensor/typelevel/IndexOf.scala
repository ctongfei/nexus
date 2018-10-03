package nexus.tensor.typelevel

import shapeless._

/**
 * Typelevel function that gets the index of the first occurrence of type [[X]] in [[L]].
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait IndexOf[L, X] extends DepFn0 {
  type Out <: Nat
  def toInt: Int
}

object IndexOf {

  def apply[L, X](implicit o: IndexOf[L, X]): Aux[L, X, o.Out] = o
  type Aux[L, X, Out0 <: Nat] = IndexOf[L, X] { type Out = Out0 }

  implicit def indexOfHListCase0[T <: HList, X]: Aux[X :: T, X, _0] =
    new IndexOf[X :: T, X] {
      type Out = _0
      def apply() = Nat._0
      def toInt = 0
    }

  implicit def indexOfHListCaseN[T <: HList, H, X, I <: Nat]
  (implicit p: IndexOf.Aux[T, X, I]): Aux[H :: T, X, Succ[I]] =
    new IndexOf[H :: T, X] {
      type Out = Succ[I]
      def apply() = Succ[I]()
      def toInt = p.toInt + 1
    }

  implicit def indexOfTuple[T, Th <: HList, X, I <: Nat]
  (implicit h: ToHList.Aux[T, Th], i: IndexOf.Aux[Th, X, I]): Aux[T, X, I] =
    new IndexOf[T, X] {
      type Out = I
      def toInt = i.toInt
      def apply(): I = i.apply()
    }

}
