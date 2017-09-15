package nexus.op

import nexus._
import nexus.func._

/**
 * Sine on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sin extends PolyDOp1[SinF] {
  /**
   * Elementwise sine on a tensor.
   */
  object Elementwise extends PolyDOp1[ESinF]
}

/**
 * Cosine on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Cos extends PolyDOp1[CosF] {
  /**
   * Elementwise cosine on a tensor.
   */
  object Elementwise extends PolyDOp1[ECosF]
}
