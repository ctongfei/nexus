package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import shapeless.Nat

object SumAlong extends ParamPolyOp1 {

  implicit def tensor[T[_], R, A, X: Label, N <: Nat, B]
    (implicit T: IsRealTensorH[T, R], ix: IndexOf.Aux[A, X, N], ra: RemoveAt.Aux[A, N, B]):
  F[X, T[A], T[B]] = (x: X) => new Op1[T[A], T[B]] {
    def name = s"SumAlong[${objTypeName(x)}]"
    def tag(tx: Type[T[A]]) = T.ground[B]
    def differentiable = true
    def forward(x: T[A]) = ??? // T.sumAlong(x, ix.toInt)
    def backward(dy: T[B], y: T[B], x: T[A]) = ??? // = T.expandDim(dy, ix.toInt, T.size(x, ix.toInt))
  }

}

object ArgMaxAlong extends ParamPolyOp1 {

  implicit def tensor[TR[_], R, TI[_], I, A, X: Label, N <: Nat, B]
  (implicit TR: IsRealTensorH[TR, R], TI: IsIntTensorH[TI, I], ix: IndexOf.Aux[A, X, N], ra: RemoveAt.Aux[A, N, B]):
  F[X, TR[A], TI[B]] = ???

}
