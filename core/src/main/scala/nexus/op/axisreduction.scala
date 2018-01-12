package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import nexus.exception._
import shapeless.Nat

object SumAlong extends ParameterizedPolyOp1 {

  implicit def sumAlongF[T[_], R, A, U: Label, N <: Nat, B]
    (implicit T: IsRealTensorK[T, R], ix: IndexOf.Aux[A, U, N], ra: RemoveAt.Aux[A, N, B]) = (u: U) =>
    new F[T[A], T[B]] {
      def name = s"SumAlong[${objTypeName(u)}]"
      def tag(tx: Type[T[A]]) = T.ground[B]
      def forward(x: T[A]) = ??? // T.sumAlong(x, ix.toInt)
      def backward(dy: T[B], y: T[B], x: T[A]) = ??? // = T.expandDim(dy, ix.toInt, T.size(x, ix.toInt))
    }

}

object MeanAlong extends ParameterizedPolyOp1 {
  implicit def meanAlongF[T[_], R, A, U: Label, N <: Nat, B]
  (implicit T: IsRealTensorK[T, R], ix: IndexOf.Aux[A, U, N], ra: RemoveAt.Aux[A, N, B]) = (u: U) =>
    new F[T[A], T[B]] {
      def name = s"MeanAlong[${objTypeName(u)}]"
      def tag(tx: Type[T[A]]) = T.ground[B]
      def forward(x: T[A]) = ???
      def backward(dy: T[B], y: T[B], x: T[A]) = ???
    }
}


object ArgMaxAlong extends ParameterizedPolyOp1 {

  implicit def argmaxAlongF[TR[_], R, TI[_], I, A, U: Label, N <: Nat, B]
  (implicit TR: IsRealTensorK[TR, R], TI: IsIntTensorK[TI, I], ix: IndexOf.Aux[A, U, N], ra: RemoveAt.Aux[A, N, B]) = (u: U) =>
    new F[TR[A], TI[B]] {
      def name = s"ArgMaxAlong[${objTypeName(u)}"
      def tag(tx: Type[TR[A]]) = TI.ground[B]
      override def differentiable = false
      def forward(x: TR[A]) = ???
      def backward(dy: TI[B], y: TI[B], x: TR[A]) = throw new OperatorNotDifferentiableException(name, 1)
    }

}

