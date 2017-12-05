package nexus.op

import nexus._
import nexus.func._


/**
 * Rectified linear unit.
 * - Shape: `RealTensor[As] => RealTensor[As]`
 * - Input: Real tensor 「bb"x"」 with axes `As`.
 * - Output: Real tensor 「bb"y」, axes `As`, computed as.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ReLU extends PolyDOp1[ReLUF.Op]

/**
 * Sigmoid (a.k.a. logistic) activation function that maps any real output to the interval (0, 1).
 *
 * Input: any tensor 「bb"x"」.
 *
 * Output: a tensor 「bb"y"」, of the same shape as 「bb"x"」, computed as
 * 「y_i = 1/(1 + e^(-x_i))」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sigmoid extends PolyDOp1[SigmoidF.Op]

/**
 * Softplus activation function.
 *
 * Input: any tensor 「bb"x"」.
 *
 * Output: a tensor 「bb"y"」, of the same shape as 「bb"x"」, computed as
 * 「y_i = log(1 + exp x_i)」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object SoftPlus extends PolyDOp1[SoftPlusF.Op]

/**
 * Softmax activation function.
 *
 * Input: a vector 「bb"x"」.
 *
 * Output: a vector 「bb"y"」, of the same size as 「bb"x"」, computed as
 * 「y_i = (exp x_i) / (sum_j exp x_j)」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Softmax extends PolyDOp1[SoftmaxF.Op]
