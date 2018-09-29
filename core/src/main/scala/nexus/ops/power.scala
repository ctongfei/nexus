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
object Sqr extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Sqr"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.sqr(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * x * 2
  def forwardTR[T[_], E, A](x: T[A])(implicit T: IsRealTensorK[T, E]) = T.eSqr(x)
  def backwardTR[T[_], E, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, E]) = dy |*| x :* 2
}

object Sqrt extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Sqrt"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.sqrt(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.inv(y) * 0.5
  def forwardTR[T[_], E, A](x: T[A])(implicit T: IsRealTensorK[T, E]) = T.eSqrt(x)
  def backwardTR[T[_], E, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, E]) = dy |*| T.eInv(y) :* 0.5
}

// TODO: Power
