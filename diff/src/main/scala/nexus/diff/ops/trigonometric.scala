package nexus.diff.ops

import nexus.diff._
import nexus.diff.ops.mixin._
import nexus._
import nexus.syntax._

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

object Tan extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Tan"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.tan(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy / R.sqr(R.cos(x))
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.tan(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |/| T.sqr(T.cos(x))
}

object ArcSin extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "ArcSin"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.arcsin(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy / R.sqrt(R.one - R.sqr(x))
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.arcsin(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |/| T.sqrt(T.addScalar(T.neg(T.sqr(x)), T.R.one))
}

object ArcCos extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "ArcCos"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.arccos(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = -ArcSin.backwardR(dy, y, x)
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.arccos(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = -ArcSin.backwardTR(dy, y, x)
}

object ArcTan extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "ArcTan"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.arctan(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy / (R.one + R.sqr(x))
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.arctan(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |/| T.addScalar(T.sqr(x), T.R.one)
}

object ATan2 extends PolyOp2
