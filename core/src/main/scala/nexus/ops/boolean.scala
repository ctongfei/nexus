package nexus.ops

import nexus._
import nexus.exception._
import nexus.ops.mixin._

/**
 * Boolean negation.
 */
object Not extends PolyOp1 with BoolElementwisePolyOp1Mixin {
  def name = "Not"
  def forwardB[B](x: B)(implicit B: IsBool[B]) = B.not(x)
  def forwardTB[T[_], B, A](x: T[A])(implicit T: IsBoolTensorK[T, B]) = T.eNot(x)

}

/**
 * Boolean conjunction.
 */
object And extends PolyOp2 with BoolElementwisePolyOp2Mixin {
  def name = "And"
  def forwardB[B](x1: B, x2: B)(implicit B: IsBool[B]) = B.and(x1, x2)
  def forwardTB[T[_], B, A](x1: T[A], x2: T[A])(implicit T: IsBoolTensorK[T, B]) = T.eAnd(x1, x2)
}

/**
 * Boolean disjunction.
 */
object Or extends PolyOp2 with BoolElementwisePolyOp2Mixin {
  def name = "Or"
  def forwardB[B](x1: B, x2: B)(implicit B: IsBool[B]) = B.or(x1, x2)
  def forwardTB[T[_], B, A](x1: T[A], x2: T[A])(implicit T: IsBoolTensorK[T, B]) = T.eOr(x1, x2)
}

/**
 * Boolean exclusive disjunction.
 */
object Xor extends PolyOp2 with BoolElementwisePolyOp2Mixin {
  def name = "Xor"
  def forwardB[B](x1: B, x2: B)(implicit B: IsBool[B]) = B.xor(x1, x2)
  def forwardTB[T[_], B, A](x1: T[A], x2: T[A])(implicit T: IsBoolTensorK[T, B]) = T.eXor(x1, x2)
}
