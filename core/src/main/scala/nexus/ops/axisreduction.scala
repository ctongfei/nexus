package nexus.ops

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import nexus.exception._
import shapeless._

object SumAlong extends ParameterizedPolyOp1 {

  implicit def sumAlongF[T[_], R, A, U <: Dim, B]
    (implicit T: IsRealTensorK[T, R], r: Remove.Aux[A, U, B]) = (u: U) =>
    new F[T[A], T[B]] {
      type Tag[t] = IsRealTensor[t, R]
      def name = n"SumAlong[$u]"
      def tag = T.ground[B]
      def forward(x: T[A]) = ??? // T.sumAlong(x, ix.toInt)
      def backward(dy: T[B], y: T[B], x: T[A]) = ??? //T.expandDim(dy, ix.toInt, T.size(x, ix.toInt))
    }

}

object ProdAlong extends ParameterizedPolyOp1 {

}

object MeanAlong extends ParameterizedPolyOp1 {
  implicit def meanAlongF[T[_], R, A, U <: Dim, B]
  (implicit T: IsRealTensorK[T, R], r: Remove.Aux[A, U, B]) = (u: U) =>
    new F[T[A], T[B]] {
      type Tag[t] = IsRealTensor[t, R]
      def name = n"MeanAlong[$u]"
      def tag = T.ground[B]
      def forward(x: T[A]) = ???
      def backward(dy: T[B], y: T[B], x: T[A]) = ???
    }
}

object ArgMaxAlong extends ParameterizedPolyOp1

object ArgMinAlong extends ParameterizedPolyOp1

object MaxAlong extends ParameterizedPolyOp1

object MinAlong extends ParameterizedPolyOp1

