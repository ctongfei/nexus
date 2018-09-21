package nexus.ops

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * Exponentiation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Exp extends PolyElementwiseOp1[IsReal, IsRealTensorK] {
  def name = "Exp"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.exp(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * y
  def forwardElementwise[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eExp(x)
  def backwardElementwise[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |*| y
}

/**
 * Natural logarithm.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Log extends PolyElementwiseOp1[IsReal, IsRealTensorK] {
  def name = "Log"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.log(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy / x
  def forwardElementwise[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eLog(x)
  def backwardElementwise[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |/| x
}

object Expm1 extends PolyElementwiseOp1[IsReal, IsRealTensorK] {
  def name = "Expm1"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.expm1(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * y
  def forwardElementwise[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eExpm1(x)
  def backwardElementwise[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |*| y
}

object Log1p extends PolyElementwiseOp1[IsReal, IsRealTensorK] {
  def name = "Log1p"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.log1p(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.inv(x + R.one)
  def forwardElementwise[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eLog1p(x)
  def backwardElementwise[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |*| T.eInv(T.addS(x, T.R.one))
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

