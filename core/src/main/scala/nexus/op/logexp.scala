package nexus.op

import nexus._
import nexus.func._


/**
 * Element-wise exponentiation.
 *
 * Input: Any tensor 「bb"x"」 with axes 「i_1 , ... , i_d」.
 *
 * Output: A tensor 「bb"y"」 with the same shape as 「bb"x"」, computed as
 * 「y_(i_1, ..., i_d) = exp(x_(i_1, ..., i_d))」.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Exp extends PolyDOp1[ExpF] {
  object Elementwise extends PolyDOp1[EExpF]
}

object Log extends PolyDOp1[LogF] {
  object Elementwise extends PolyDOp1[ELogF]
}
