package nexus.op

import nexus._
import shapeless._
import shapeless.ops.hlist._

/**
 * General tensor multiplication that marginalizes out all axes between two tensors that match.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object TMul extends GenOp2[TMulF]

trait TMulF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "TMul"
}

object TMulF {

  class CPUTMulF[D, A <: HList, B <: HList, C <: HList](env: Env[cpu.UntypedDenseTensor, D]) extends TMulF[cpu.DenseTensor[D, A], cpu.DenseTensor[D, B], cpu.DenseTensor[D, C]] {
    import cpu._
    def forward(x1: DenseTensor[D, A], x2: DenseTensor[D, B]) = ???
    def backward1(dy: DenseTensor[D, C], y: DenseTensor[D, C], x1: DenseTensor[D, A], x2: DenseTensor[D, B]) = ???
    def backward2(dy: DenseTensor[D, C], y: DenseTensor[D, C], x1: DenseTensor[D, A], x2: DenseTensor[D, B]) = ???
  }

  //implicit def cpuTMulF[D, A <: HList, B <: HList, C <: HList]
  //(implicit env: Env[cpu.UntypedDenseTensor, D], su: SymmetricDiff.Aux[A, B, C]) = new CPUTMulF[D, A, B, C](env)


}
