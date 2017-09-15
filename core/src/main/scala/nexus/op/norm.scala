package nexus.op

import nexus._
import nexus.func._

/**
 * Absolute value.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Abs extends PolyDOp1[AbsF] {
  object Elementwise extends PolyDOp1[EAbsF]
}

/**
 * 「L_1」 (Manhattan) normalization.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object L1Normalize extends PolyDOp1[L1NormalizeF]

/**
 * 「L_2」 (Euclidean) distance between two vectors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object L2Distance extends PolyDOp2[L2DistanceF]

