package nexus.op

import nexus._

/**
 * Adds two scalars or two tensors of the same axes/shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Add extends PolyDOp2[AddF]
/**
 * Subtracts two scalars or two tensors of the same axes/shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sub extends PolyDOp2[SubF]
/**
 * Scalar multiplication.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Mul extends PolyDOp2[MulF]
/**
 * Scalar division.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Div extends PolyDOp2[DivF]
/**
 * Element-wise multiplication (a.k.a. Hadamard product) of two tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object EMul extends PolyDOp2[EMulF]
/**
 * Element-wise division of two tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object EDiv extends PolyDOp2[EDivF]

