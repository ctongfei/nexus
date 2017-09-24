package nexus.op

import nexus._
import nexus.algebra._

/**
 * Dropout.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Dropout(parameter: Double) extends ParaPolyDOp1[Double, DropoutF]
