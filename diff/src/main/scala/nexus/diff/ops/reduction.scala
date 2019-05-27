package nexus.diff.ops

import nexus.diff._
import nexus.diff.exception._
import nexus.diff.ops.mixin._
import nexus._
import nexus.typelevel.Remove.Aux
import nexus.typelevel._

object Sum {

  object All extends PolyOp1 {
    implicit def sumAllF[T[_], R, A](implicit T: IsRealTensorK[T, R]): P[T[A], R] =
      new P[T[A], R] {
        def name = "Sum"
        def tag = Tag.real[R]
        def forward(x: T[A]) = T.sum(x)
        def backward(dy: R, y: R, x: T[A]) = ???
      }
  }

  object Along extends ParameterizedPolyOp1 with RealAxisReductionOpMixin {
    def name = "SumAlong"
    def forwardR[T[_], R, U, I <: Dim, V](x: T[U], dim: I)(implicit T: IsRealTensorK[T, R], rx: Remove.Aux[U, I, V]): T[V] = ???
    def backwardR[T[_], R, U, I <: Dim, V](dy: T[V], y: T[V], x: T[U])(implicit T: IsRealTensorK[T, R], rx: Remove.Aux[U, I, V]) = ???
  }

}

object ProdAlong extends ParameterizedPolyOp1 {

}

object Mean {

  object Along extends ParameterizedPolyOp1 with RealAxisReductionOpMixin {
    def name = "MeanAlong"
    def forwardR[T[_], R, U, I <: Dim, V](x: T[U], dim: I)(implicit T: IsRealTensorK[T, R], rx: Aux[U, I, V]) = ???
    def backwardR[T[_], R, U, I <: Dim, V](dy: T[V], y: T[V], x: T[U])(implicit T: IsRealTensorK[T, R], rx: Aux[U, I, V]) = ???
  }

}

object ArgMaxAlong extends ParameterizedPolyOp1 {

  implicit def argmaxAlongF[TR[_], R, TZ[_], Z, a, u <: Dim, b]
  (implicit TR: IsRealTensorK[TR, R], TZ: IsIntTensorK[TZ, Z], r: Remove.Aux[a, u, b]) = (u: u) =>
    new P[TR[a], TZ[b]] {
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

