package nexus.op

import nexus._
import nexus.func._

/**
 * Sine on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sin extends PolyDOp1[SinF.Op] {
  /**
   * Elementwise sine on a tensor.
   */
  object Elementwise extends PolyDOp1[SinF.Elementwise.Op]
}

/**
 * Cosine on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Cos extends PolyDOp1[CosF.Op] {
  /**
   * Elementwise cosine on a tensor.
   */
  object Elementwise extends PolyDOp1[CosF.Elementwise.Op]
}

/**
 * Tangent on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Tan extends PolyDOp1[TanF.Op] {
  /**
   * Elementwise tangent on a tensor.
   */
  object Elementwise extends PolyDOp1[TanF.Elementwise.Op]
}
