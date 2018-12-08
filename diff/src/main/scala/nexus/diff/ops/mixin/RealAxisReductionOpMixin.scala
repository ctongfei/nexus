package nexus.diff.ops.mixin

import nexus.diff._
import nexus.tensor._
import nexus.tensor.typelevel._

/**
 * @author Tongfei Chen
 */
trait RealAxisReductionOpMixin { poly: ParameterizedPolyOp1 =>

  def forwardR[T[_], R, A, U <: Dim, B](x: T[A], u: U)
                                       (implicit T: IsRealTensorK[T, R], rx: Remove.Aux[A, U, B]): T[B]

  def backwardR[T[_], R, A, U <: Dim, B](dy: T[B], y: T[B], x: T[A])
                                        (implicit T: IsRealTensorK[T, R], rx: Remove.Aux[A, U, B]): T[A]

  implicit def fR[T[_], R, A, U <: Dim, B]
  (implicit T: IsRealTensorK[T, R], rx: Remove.Aux[A, U, B]): U => F[T[A], T[B]] =
    (u: U) => new F[T[A], T[B]] {
      def name = n"SumAlong[$u]"
      def tag = Tag.realTensor[T, R, B]
      def forward(x: T[A]) = poly.forwardR(x, u)
      def backward(dy: T[B], y: T[B], x: T[A]) = poly.backwardR(dy, y, x)
    }

}
