package nexus.op

import nexus._

/**
 * Negation of any tensor.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Neg extends GenOp1[NegF]

trait NegF[X, Y] extends Op1[X, Y]

object NegF {

}

