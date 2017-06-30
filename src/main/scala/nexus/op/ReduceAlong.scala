package nexus.op

import nexus._
import nexus.cpu.DenseTensor
import shapeless._

/**
 * @author Tongfei Chen
 */
object ReduceSumAlong extends ArgGenOp1[ReduceSumAlongF]

trait ReduceSumAlongF[Int, X, Y] extends ArgOp1[Int, X, Y]

object ReduceSumAlongF {

}