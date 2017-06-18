package nexus.op

import nexus._
import shapeless.HList

/**
 * Clipping all elements in a tensor to lie in the range [min, max].
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Clip extends ArgGenOp1[ClipF] {

  def apply[X, Y](min: Double, max: Double)(implicit ev: ClipF[ClipArg, X, Y]): Op1[X, Y] =
    ev(new ClipArg(min, max))

}

class ClipArg(val min: Double, val max: Double)

trait ClipF[Arg, X, Y] extends ArgOp1[Arg, X, Y]
object ClipF {
}
