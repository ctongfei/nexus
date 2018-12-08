package nexus.diff.ops

import nexus.diff._
import nexus.diff.ops.mixin._
import nexus.tensor._
import nexus.tensor.syntax._

/**
 * Sine function.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sin extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Sin"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.sin(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.cos(x)
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.sin(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |*| T.cos(x)
}


/**
 * Cosine function.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Cos extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Cos"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.cos(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = -dy * R.sin(x)
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.cos(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = -dy |*| T.sin(x)
}

object Tan extends PolyOp1

object ArcSin extends PolyOp1

object ArcCos extends PolyOp1

object ArcTan extends PolyOp1

object ATan2 extends PolyOp2
