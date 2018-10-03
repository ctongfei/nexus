package nexus.tensor.typelevel

import shapeless._

/**
 * A simpler [[shapeless.ops.hlist.Length]].
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Len[A] {
  type Out <: Nat
  def apply(): Int
}

object Len {

  def apply[A](implicit l: Len[A]): Aux[A, l.Out] = l
  type Aux[A, N <: Nat] = Len[A] { type Out = N }

  implicit def lenHListCase0: Len.Aux[HNil, _0] = new Len[HNil] {
    type Out = _0
    def apply() = 0
  }

  implicit def lenHListCaseN[Ah, At <: HList, P <: Nat](implicit t: Len.Aux[At, P]): Len.Aux[Ah :: At, Succ[P]] =
    new Len[Ah :: At] {
      type Out = Succ[P]
      override def apply() = t() + 1
    }

  implicit def lenTuple[A, Al <: HList, N <: Nat](implicit al: ToHList.Aux[A, Al], h: Len.Aux[Al, N]): Len.Aux[A, N] =
    new Len[A] {
      type Out = N
      def apply() = h()
    }

}
