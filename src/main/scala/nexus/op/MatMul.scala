package nexus.op

import nexus._
import shapeless._

/**
 * Matrix multiplication of two matrices (2-D tensors).
 * @note The second axis of the first operand and the first axis of the second operand must match.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object MatMul extends GenOp2[MatMulF]

trait MatMulF[X1, X2, Y] extends Op2[X1, X2, Y]

object MatMulF {
  implicit def numeric[T[_, _], D, A, B, C](implicit env: Env[T, D]): MatMulF[T[D, A :: B :: HNil], T[D, B :: C :: HNil], T[D, A :: C :: HNil]] =
    new MatMulF[T[D, A :: B :: HNil], T[D, B :: C :: HNil], T[D, A :: C :: HNil]] {
      type X1 = T[D, A :: B :: HNil]
      type X2 = T[D, B :: C :: HNil]
      type Y = T[D, A :: C :: HNil]
      def forward(x1: X1, x2: X2): Y = ???
      def backward1(dy: Y, y: Y, x1: X1, x2: X2) = ??? // dy matmul x2.transpose
      def backward2(dy: Y, y: Y, x1: X1, x2: X2) = ??? // dy matmul x1.transpose
    }
}

