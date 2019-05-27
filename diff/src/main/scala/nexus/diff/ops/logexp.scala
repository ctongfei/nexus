package nexus.diff.ops

import nexus.diff._
import nexus.diff.ops.mixin._
import nexus._
import nexus.syntax._

/**
 * Exponentiation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Exp extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Exp"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.exp(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * y
  def forwardTR[T[_], R, I](x: T[I])(implicit T: IsRealTensorK[T, R]) = T.exp(x)
  def backwardTR[T[_], R, I](dy: T[I], y: T[I], x: T[I])(implicit T: IsRealTensorK[T, R]) = dy |*| y
}

/**
 * Natural logarithm.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Log extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Log"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.log(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy / x
  def forwardTR[T[_], R, I](x: T[I])(implicit T: IsRealTensorK[T, R]) = T.log(x)
  def backwardTR[T[_], R, I](dy: T[I], y: T[I], x: T[I])(implicit T: IsRealTensorK[T, R]) = dy |/| x
}

object Expm1 extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Expm1"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.expm1(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * y
  def forwardTR[T[_], R, I](x: T[I])(implicit T: IsRealTensorK[T, R]) = T.expm1(x)
  def backwardTR[T[_], R, I](dy: T[I], y: T[I], x: T[I])(implicit T: IsRealTensorK[T, R]) = dy |*| y
}

object Log1p extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Log1p"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.log1p(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.inv(x + R.one)
  def forwardTR[T[_], R, I](x: T[I])(implicit T: IsRealTensorK[T, R]) = T.log1p(x)
  def backwardTR[T[_], R, I](dy: T[I], y: T[I], x: T[I])(implicit T: IsRealTensorK[T, R]) = dy |*| T.inv(T.addScalar(x, T.R.one))
}

object LogSumExp extends PolyOp1 {

  implicit def logSumExpF[T[_], R, I](implicit T: IsRealTensorK[T, R]): P[T[I], R] = new P[T[I], R] {
    def name = "LogSumExp"
    def tag = Tag.real[R]
    def forward(x: T[I]) = ???
    def backward(dy: R, y: R, x: T[I]) = ???
  }

}
