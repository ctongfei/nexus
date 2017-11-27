package nexus.op

import nexus._
import nexus.func._

/**
 * Scales a tensor by a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Scale extends PolyDOp2[ScaleF] {
  //def By[R](k: R) = Curried(k)
}

/**
 * Inner product of two tensors.
 *
 * Inputs: two tensors 「bb "a"」 and 「bb "b"」 with the same axes and shape.
 *
 * Output: a scalar, computed as 「y = bb"a" cdot bb"b" = sum_(i_1 i_2 ... i_d)  a_(i_1 ... i_d) b_(i_1 ... i_d)」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Dot extends PolyDOp2[DotF]

/**
 * Matrix multiplication of two matrices (2-D tensors).
 * @note The second axis of the first operand and the first axis of the second operand must match.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object MatMul extends PolyDOp2[MatMulF]


/**
 * Transposition of a matrix (2-D tensor).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Transpose extends PolyDOp1[TransposeF]


/**
 * Matrix-vector multiplication.
 *
 * Inputs:
 *  - Matrix \(\mathbf{X}_1\) with axes and shape \((B \to m, A \to n)\).
 *  - Vector \(\mathbf{x}_2\) with axes and shape \((A \to n)\).
 *
 * Output:
 *  - Vector \(\mathbf{y} = \mathbf{X}_1 \mathbf{x}_2\), with shape \((B \to m)\).
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object MVMul extends PolyDOp2[MVMulF]

/**
 * General tensor multiplication (contraction) that marginalizes out all axes between two tensors that match.
 * Einstein summation
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Contract extends PolyDOp2[ContractF]