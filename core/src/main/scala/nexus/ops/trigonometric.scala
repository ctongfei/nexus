package nexus.ops

import nexus._
import nexus.ops.mixin._

/**
 * Sine on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sin extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Sin"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.sin(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.cos(x)
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eSin(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |*| T.eCos(x)
}


/**
 * Cosine on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Cos extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Cos"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.cos(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = -dy * R.sin(x)
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eCos(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = -dy |*| T.eSin(x)
}

object Tan extends PolyOp1

object ArcSin extends PolyOp1

object ArcCos extends PolyOp1

object ArcTan extends PolyOp1

object ATan2 extends PolyOp2

