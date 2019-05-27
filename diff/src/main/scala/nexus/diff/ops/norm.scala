package nexus.diff.ops

import nexus.diff._
import nexus.diff.ops.mixin._
import nexus._
import nexus.syntax._

/**
 * Absolute value.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Abs extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Abs"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.abs(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.sgn(x)
  def forwardTR[T[_], R, I](x: T[I])(implicit T: IsRealTensorK[T, R]) = T.abs(x)
  def backwardTR[T[_], R, I](dy: T[I], y: T[I], x: T[I])(implicit T: IsRealTensorK[T, R]) = dy |*| T.sgn(x)
}


object L1Norm extends PolyOp1 {

  implicit def l1NormF[T[_], R, I](implicit T: IsRealTensorK[T, R]): P[T[I], R] =
    new P[T[I], R] {
      def name = "L1Norm"
      def tag = Tag.real[R]
      def forward(x: T[I]) = T.sum(T.abs(x))
      def backward(dy: R, y: R, x: T[I]) = T.sgn(x) :* dy
    }
}

object L2Norm extends PolyOp1 {

  implicit def l2NormF[T[_], R, I](implicit T: IsRealTensorK[T, R]): P[T[I], R] =
    new P[T[I], R] {
      def name = "L2Norm"
      def tag = Tag.real[R]
      def forward(x: T[I]) = T.R.sqrt(T.dot(x, x))
      def backward(dy: R, y: R, x: T[I]) = x :* T.R.div(dy, y)
    }
}

object LpNorm extends ParameterizedPolyOp1 {

  implicit def lpNormF[T[_], R, I](implicit T: IsRealTensorK[T, R]) = (p: Double) =>
      new P[T[I], R] {
        def name = s"LpNorm[$p]"
        def tag = Tag.real[R]
        def forward(x: T[I]) = ???
        def backward(dy: R, y: R, x: T[I]) = ???
      }

}
