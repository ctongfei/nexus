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

  class CPUReduceSumAlongF[D, A <: HList, X, B <: HList](env: Env[cpu.UntypedDenseTensor, D], axisI: Int, axisX: X) extends Op1[cpu.DenseTensor[D, A], cpu.DenseTensor[D, B]] {
    def name = s"ReduceSumAlong[axis=$axisX]"
    def forward(x: DenseTensor[D, A]) = ???
    def backward(dy: DenseTensor[D, B], y: DenseTensor[D, B], x: DenseTensor[D, A]) = ???
  }

}