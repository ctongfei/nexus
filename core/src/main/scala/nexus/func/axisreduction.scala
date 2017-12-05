package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import nexus.func.factory._
import shapeless.Nat

object SumAlongF extends AxisReductionOp1Factory[IsTypedRealTensor] {
  def name = "SumAlong"

  def forward[T[_ <: $$], E, U, A <: $$, I <: Nat, B <: $$]
    (x: T[A], u: U)
    (implicit T: IsTypedRealTensor[T, E], io: IndexOf.Aux[A, U, I], ra: RemoveAt.Aux[A, I, B]): T[B] = ???


  def backward[T[_ <: $$], E, U, A <: $$, I <: Nat, B <: $$]
    (dy: T[B], y: T[B], x: T[A], u: U)
    (implicit T: IsTypedRealTensor[T, E], io: IndexOf.Aux[A, U, I], ra: RemoveAt.Aux[A, I, B]): T[A] =
  ???
}

