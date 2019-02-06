package nexus.diff.ops.mixin

import nexus.diff._
import nexus._
import nexus.typelevel._

/**
 * @author Tongfei Chen
 */
trait RealAxisReductionOpMixin { poly: ParameterizedPolyOp1 =>

  def name: String

  def forwardR[T[_], R, U, I <: Dim, V](x: T[U], dim: I)
                                       (implicit T: IsRealTensorK[T, R], rx: Remove.Aux[U, I, V]): T[V]

  def backwardR[T[_], R, U, I <: Dim, V](dy: T[V], y: T[V], x: T[U])
                                        (implicit T: IsRealTensorK[T, R], rx: Remove.Aux[U, I, V]): T[U]

  implicit def fR[T[_], R, U, I <: Dim, V]
  (implicit T: IsRealTensorK[T, R], rx: Remove.Aux[U, I, V]): I => F[T[U], T[V]] =
    (dim: I) => new F[T[U], T[V]] {
      def name = n"${poly.name}[$dim]"
      def tag = Tag.realTensor[T, R, V]
      def forward(x: T[U]) = poly.forwardR(x, dim)
      def backward(dy: T[V], y: T[V], x: T[U]) = poly.backwardR(dy, y, x)
    }

}
