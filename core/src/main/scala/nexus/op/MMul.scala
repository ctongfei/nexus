package nexus.op

import nexus._
import nexus.impl._

/**
 * Matrix multiplication of two matrices (2-D tensors).
 * @note The second axis of the first operand and the first axis of the second operand must match.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object MMul extends PolyDOp2[MMulF]

@implicitNotFound("Cannot apply MMul to ${X1} and ${X2}.")
trait MMulF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "MMul"
}

object MMulF {
  implicit def matrix[T[_ <: $$], D, A, B, C](implicit ops: TypedRealTensorOps[T, D]): MMulF[T[A::B::$], T[B::C::$], T[A::C::$]] =
    new MMulF[T[A::B::$], T[B::C::$], T[A::C::$]] {
      import ops._
      def gradOps = ops.ground[A::C::$]
      def forward(x1: T[A::B::$], x2: T[B::C::$]) = mmMul(x1, x2)
      def backward1(dy: T[A::C::$], y: T[A::C::$], x1: T[A::B::$], x2: T[B::C::$]) = mmMul(dy, transpose(x2))
      def backward2(dy: T[A::C::$], y: T[A::C::$], x1: T[A::B::$], x2: T[B::C::$]) = mmMul(transpose(x1), dy)
    }
}
