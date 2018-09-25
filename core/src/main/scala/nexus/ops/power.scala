package nexus.ops

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * Square of a number.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sqr extends PolyElementwiseOp1[IsReal, IsRealTensorK] {
  def name = "Sqr"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.sqr(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * x * 2
  def forwardElementwise[T[_], E, A](x: T[A])(implicit T: IsRealTensorK[T, E]) = T.eSqr(x)
  def backwardElementwise[T[_], E, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, E]) = dy |*| x :* 2
}

object Sqrt extends PolyElementwiseOp1[IsReal, IsRealTensorK] {
  def name = "Sqrt"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.sqrt(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.inv(y) * 0.5
  def forwardElementwise[T[_], E, A](x: T[A])(implicit T: IsRealTensorK[T, E]) = T.eSqrt(x)
  def backwardElementwise[T[_], E, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, E]) = dy |*| T.eInv(y) :* 0.5
}

// TODO: Power
