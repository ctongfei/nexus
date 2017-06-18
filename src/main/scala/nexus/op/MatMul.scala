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

trait MatMulF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "MatMul"
}

object MatMulF {
}
