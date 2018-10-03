package nexus.ops

import nexus._
import nexus.ops.mixin._

/**
 * Exponentiation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Exp extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Exp"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.exp(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * y
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eExp(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |*| y
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
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eLog(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |/| x
}

object Expm1 extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Expm1"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.expm1(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * y
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eExpm1(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |*| y
}

object Log1p extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Log1p"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.log1p(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.inv(x + R.one)
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eLog1p(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |*| T.eInv(T.addS(x, T.R.one))
}

object LogSumExp extends PolyOp1 {

  implicit def logSumExpF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], R] = new F[T[A], R] {
    type Tag[r] = IsReal[r]
    def name = "LogSumExp"
    def tag = T.R
    def forward(x: T[A]) = ???
    def backward(dy: R, y: R, x: T[A]) = ???
  }

}

