package nexus.ops

import nexus._
import nexus.tensor._
import nexus.tensor.syntax._
import nexus.ops.mixin._

/**
 * Adds two scalars or two tensors of the same axes/shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Add extends PolyOp2 with RealElementwisePolyOp2Mixin with IntElementwisePolyOp2Mixin {
  def name = "Add"
  def forwardR[R](x1: R, x2: R)(implicit R: IsReal[R]) = R.add(x1, x2)
  def backward1R[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]) = dy
  def backward2R[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]) = dy
  def forwardTR[T[_], R, A](x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]) = T.add(x1, x2)
  def backward1TR[T[_], R, A](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]) = dy
  def backward2TR[T[_], R, A](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]) = dy
  def forwardZ[Z](x1: Z, x2: Z)(implicit Z: IsInt[Z]) = Z.add(x1, x2)
  def forwardTZ[T[_], Z, A](x1: T[A], x2: T[A])(implicit T: IsIntTensorK[T, Z]) = T.add(x1, x2)
}

/**
 * Subtracts two scalars or two tensors of the same axes/shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sub extends PolyOp2 with RealElementwisePolyOp2Mixin with IntElementwisePolyOp2Mixin {
  def name = "Sub"
  def forwardR[R](x1: R, x2: R)(implicit R: IsReal[R]) = R.sub(x1, x2)
  def backward1R[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]) = dy
  def backward2R[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]) = R.neg(dy)
  def forwardTR[T[_], R, A](x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]) = T.sub(x1, x2)
  def backward1TR[T[_], R, A](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]) = dy
  def backward2TR[T[_], R, A](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]) = T.neg(dy)
  def forwardZ[Z](x1: Z, x2: Z)(implicit Z: IsInt[Z]) = Z.sub(x1, x2)
  def forwardTZ[T[_], Z, A](x1: T[A], x2: T[A])(implicit T: IsIntTensorK[T, Z]) = T.sub(x1, x2)
}

/**
 * Multiplication for scalars or elementwise (Hadamard) multiplication for tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Mul extends PolyOp2 with RealElementwisePolyOp2Mixin with IntElementwisePolyOp2Mixin {
  def name = "Mul"
  def forwardR[R](x1: R, x2: R)(implicit R: IsReal[R]) = R.mul(x1, x2)
  def backward1R[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]) = R.mul(dy, x2)
  def backward2R[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]) = R.mul(dy, x1)
  def forwardTR[T[_], R, A](x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]) = T.eMul(x1, x2)
  def backward1TR[T[_], R, A](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]) = T.eMul(dy, x2)
  def backward2TR[T[_], R, A](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]) = T.eMul(dy, x1)
  def forwardZ[Z](x1: Z, x2: Z)(implicit Z: IsInt[Z]) = Z.mul(x1, x2)
  def forwardTZ[T[_], Z, A](x1: T[A], x2: T[A])(implicit T: IsIntTensorK[T, Z]) = T.eMul(x1, x2)
}

/**
 * Division for scalars or elementwise division for tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Div extends PolyOp2 with RealElementwisePolyOp2Mixin {
  def name = "Div"
  def forwardR[R](x1: R, x2: R)(implicit R: IsReal[R]) = R.div(x1, x2)
  def backward1R[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]) = R.div(dy, x2)
  def backward2R[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]) = -dy * y / x2
  def forwardTR[T[_], R, A](x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]) = x1 |/| x2
  def backward1TR[T[_], R, A](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]) = dy |/| x2
  def backward2TR[T[_], R, A](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]) = -dy |*| y |/| x2
}

/**
 * Negation of any scalar or tensor.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Neg extends PolyOp1 with RealElementwisePolyOp1Mixin with IntElementwisePolyOp1Mixin {
  def name = "Neg"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.neg(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = R.neg(dy)
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.neg(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = T.neg(dy)
  def forwardZ[Z](x: Z)(implicit Z: IsInt[Z]) = Z.neg(x)
  def forwardTZ[T[_], Z, A](x: T[A])(implicit T: IsIntTensorK[T, Z]) = T.neg(x)
}

/**
 * Multiplicative inverse.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Inv extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "Inv"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = R.inv(x)
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = -dy * R.sqr(y)
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eInv(x)
  def backwardTR[T[_], E, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, E]) = -dy |*| T.eSqr(y)
}
