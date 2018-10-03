package nexus.ops

import nexus._
import nexus.exception._
import nexus.ops.mixin._

object SumAlong extends ParameterizedPolyOp1 with RealAxisReductionOpMixin {
  def forwardR[T[_], R, A, U <: Dim, B](x: T[A], u: U)(implicit T: IsRealTensorK[T, R], rx: Remove.Aux[A, U, B]) = ???
  def backwardR[T[_], R, A, U <: Dim, B](dy: T[B], y: T[B], x: T[A])(implicit T: IsRealTensorK[T, R], rx: Remove.Aux[A, U, B]) = ???
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

object ArgMaxAlong extends ParameterizedPolyOp1 {

  implicit def argmaxAlongF[TR[_], R, TZ[_], Z, A, U <: Dim, B]
  (implicit TR: IsRealTensorK[TR, R], TZ: IsIntTensorK[TZ, Z], r: Remove.Aux[A, U, B]) = (u: U) =>
    new F[TR[A], TZ[B]] {
      type Tag[tzb] = IsTensor[tzb, Z] // TODO: IsIntTensor
      def name = s"ArgMaxAlong[${typeName(u)}"
      def tag = TZ.ground[B]
      override def differentiable = false
      def forward(x: TR[A]) = ???
      def backward(dy: TZ[B], y: TZ[B], x: TR[A]) = throw new OperatorNotDifferentiableException(this, 1)
    }

}

object ArgMinAlong extends ParameterizedPolyOp1

object MaxAlong extends ParameterizedPolyOp1

object MinAlong extends ParameterizedPolyOp1

