package nexus.op

import nexus._
import nexus.func._

/**
 * Identity function.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Id extends PolyDOp1[IdF.Op]

/**
 * Adds two scalars or two scalars of the same axes/shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Add extends PolyDOp2[AddF.Op]

/**
 * Subtracts two scalars or two tensors of the same axes/shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sub extends PolyDOp2[SubF.Op]

/**
 * Scalar multiplication.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Mul extends PolyDOp2[MulF.Op] {
  /**
   * Element-wise multiplication (a.k.a. Hadamard product) between two tensors.
   */
  object Elementwise extends PolyDOp2[MulF.Elementwise.Op]
}

/**
 * Scalar division.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Div extends PolyDOp2[DivF.Op] {
  /**
   * Element-wise division between two tensors.
   */
  object Elementwise extends PolyDOp2[DivF.Elementwise.Op]
}

/**
 * Negation of any scalar or tensor.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Neg extends PolyDOp1[NegF.Op] {
  val Elementwise = Neg
}

/**
 * Scalar multiplicative inverse.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Inv extends PolyDOp1[InvF.Op] {

  /**
   * Element-wise multiplicative inverse.
   */
  object Elementwise extends PolyDOp1[InvF.Elementwise.Op]
}
