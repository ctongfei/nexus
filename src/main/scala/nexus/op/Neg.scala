package nexus.op

import nexus._
import nexus.cpu.DenseTensor
import shapeless.HList

/**
 * Negation of any tensor.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Neg extends GenOp1[NegF]

trait NegF[X, Y] extends Op1[X, Y] {
  def name = "Neg"
}

object NegF {

  class CPUNegF[D, A <: HList](env: Env[cpu.UntypedDenseTensor, D]) extends NegF[cpu.DenseTensor[D, A], cpu.DenseTensor[D, A]] {
    def forward(x: DenseTensor[D, A]) = env.neg(x) typeWith x.axes
    def backward(dy: DenseTensor[D, A], y: DenseTensor[D, A], x: DenseTensor[D, A]) = env.neg(dy) typeWith x.axes
  }

  implicit def cpuNegF[D, A <: HList](implicit env: Env[cpu.UntypedDenseTensor, D]): NegF[cpu.DenseTensor[D, A], cpu.DenseTensor[D, A]] = new CPUNegF(env)

}

