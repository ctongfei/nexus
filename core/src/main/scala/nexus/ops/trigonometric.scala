package nexus.ops

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * Sine on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sin extends PolyElementwiseOp1[IsReal, IsRealTensorK] {
  def name = "Sin"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.sin(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.cos(x)
  def forwardElementwise[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eSin(x)
  def backwardElementwise[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |*| T.eCos(x)
}


/**
 * Cosine on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Cos extends PolyElementwiseOp1[IsReal, IsRealTensorK] {
  def name = "Cos"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.cos(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = -dy * R.sin(x)
  def forwardElementwise[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eCos(x)
  def backwardElementwise[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = -dy |*| T.eSin(x)
}

object Tan extends PolyOp1

object ArcSin extends PolyOp1

object ArcCos extends PolyOp1

object ArcTan extends PolyOp1

object ATan2 extends PolyOp2

