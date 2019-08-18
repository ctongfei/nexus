package nexus.diff.ops

import nexus.diff._
import nexus.diff.ops.mixin._
import nexus._
import nexus.syntax._

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
  def forwardTR[T[_], R, I](x: T[I])(implicit T: IsRealTensorK[T, R]) = T.sqr(x)
  def backwardTR[T[_], R, I](dy: T[I], y: T[I], x: T[I])(implicit T: IsRealTensorK[T, R]) = dy |*| x :* 2
}

object Sqrt extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Sqrt"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.sqrt(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.recip(y) * 0.5
  def forwardTR[T[_], R, I](x: T[I])(implicit T: IsRealTensorK[T, R]) = T.sqrt(x)
  def backwardTR[T[_], R, I](dy: T[I], y: T[I], x: T[I])(implicit T: IsRealTensorK[T, R]) = dy |*| T.inv(y) :* 0.5
}

// TODO: Power
