package nexus.typelevel

import shapeless._

/**
 * Typelevel function that gets the index of the first occurrence of type [[U]] in [[A]].
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait IndexOf[A, U] extends DepFn0 {
  type Out <: Nat
  def toInt: Int
}

object IndexOf {

  def apply[A, U](implicit o: IndexOf[A, U]): Aux[A, U, o.Out] = o
  type Aux[A, U, I <: Nat] = IndexOf[A, U] { type Out = I }

  implicit def case0[At <: HList, U]: Aux[U :: At, U, _0] =
    new IndexOf[U :: At, U] {
      type Out = _0
      def apply() = Nat._0
      def toInt = 0
    }

  implicit def caseN[At <: HList, Ah, U, I <: Nat]
  (implicit p: IndexOf.Aux[At, U, I]): Aux[Ah :: At, U, Succ[I]] =
    new IndexOf[Ah :: At, U] {
      type Out = Succ[I]
      def apply() = Succ[I]()
      def toInt = p.toInt + 1
    }

  implicit def tuple[A, Al <: HList, U, I <: Nat]
  (implicit h: ToHList.Aux[A, Al], i: IndexOf.Aux[Al, U, I]): Aux[A, U, I] =
    new IndexOf[A, U] {
      type Out = I
      def toInt = i.toInt
      def apply(): I = i.apply()
    }

}
