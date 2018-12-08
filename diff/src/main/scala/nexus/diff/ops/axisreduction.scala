package nexus.diff.ops

import nexus.diff._
import nexus.diff.exception._
import nexus.diff.ops.mixin._
import nexus.tensor._
import nexus.tensor.typelevel._

object SumAlong extends ParameterizedPolyOp1 with RealAxisReductionOpMixin {
  def forwardR[T[_], R, A, U <: Dim, B](x: T[A], u: U)(implicit T: IsRealTensorK[T, R], rx: Remove.Aux[A, U, B]) = ???
  def backwardR[T[_], R, A, U <: Dim, B](dy: T[B], y: T[B], x: T[A])(implicit T: IsRealTensorK[T, R], rx: Remove.Aux[A, U, B]) = ???
}

object ProdAlong extends ParameterizedPolyOp1 {

}

object MeanAlong extends ParameterizedPolyOp1 {
  implicit def meanAlongF[T[_], R, a, u <: Dim, b]
  (implicit T: IsRealTensorK[T, R], r: Remove.Aux[a, u, b]) = (u: u) =>
    new F[T[a], T[b]] {
      type Tag[t] = IsRealTensor[t, R]
      def name = n"MeanAlong[$u]"
      def tag = Tag.realTensor[T, R, b]
      def forward(x: T[a]) = ???
      def backward(dy: T[b], y: T[b], x: T[a]) = ???
    }
}

object ArgMaxAlong extends ParameterizedPolyOp1 {

  implicit def argmaxAlongF[TR[_], R, TZ[_], Z, a, u <: Dim, b]
  (implicit TR: IsRealTensorK[TR, R], TZ: IsIntTensorK[TZ, Z], r: Remove.Aux[a, u, b]) = (u: u) =>
    new F[TR[a], TZ[b]] {
      def name = s"ArgMaxAlong[${typeName(u)}"
      def tag = Tag.tensor[TZ, Z, b] // TODO: intTensor
      override def differentiable = false
      def forward(x: TR[a]) = ???
      def backward(dy: TZ[b], y: TZ[b], x: TR[a]) = throw new OperatorNotDifferentiableException(this, 1)
    }

}

object ArgMinAlong extends ParameterizedPolyOp1

object MaxAlong extends ParameterizedPolyOp1

object MinAlong extends ParameterizedPolyOp1

