package nexus.algebra.typelevel

import nexus.algebra._
import shapeless.{HNil, HList, Nat, Succ, _0}

/**
 * A simpler [[shapeless.ops.hlist.Length]] without using the `Aux` pattern.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Len[L] {
  type Out <: Nat
  def apply(): Int
}

object Len {
  def apply[L](implicit l: Len[L]): Len[L] = l
  type Aux[L, N <: Nat] = Len[L] { type Out = N }

  implicit def lenHListCase0: Len.Aux[HNil, _0] = new Len[HNil] {
    type Out = _0
    def apply() = 0
  }

  implicit def lenHListCaseN[H, T <: HList, P <: Nat](implicit t: Len.Aux[T, P]): Len.Aux[H :: T, Succ[P]] = new Len[H :: T] {
    type Out = Succ[P]
    override def apply() = t() + 1
  }

  implicit def lenTuple[A, Ah <: HList, N <: Nat](implicit ah: ToHList.Aux[A, Ah], h: Len.Aux[Ah, N]): Len.Aux[A, N] = new Len[A] {
    type Out = N
    def apply() = h()
  }

}
