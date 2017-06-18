package nexus.op

import nexus._
import nexus.cpu.DenseTensor
import shapeless.HList

/**
 * Subtraction of two tensors of the same axes and shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sub extends GenOp2[SubF]

trait SubF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "Sub"
}

object SubF {

  class CPUSubF[D, A <: HList](env: Env[cpu.UntypedDenseTensor, D]) extends SubF[cpu.DenseTensor[D, A], cpu.DenseTensor[D, A], cpu.DenseTensor[D, A]] {
    def forward(x1: DenseTensor[D, A], x2: DenseTensor[D, A]) = env.sub(x1, x2) typeWith x1.axes
    def backward1(dy: DenseTensor[D, A], y: DenseTensor[D, A], x1: DenseTensor[D, A], x2: DenseTensor[D, A]) = dy
    def backward2(dy: DenseTensor[D, A], y: DenseTensor[D, A], x1: DenseTensor[D, A], x2: DenseTensor[D, A]) = env.neg(dy) typeWith x2.axes
  }

  implicit def cpuSubF[D, A <: HList](implicit env: Env[cpu.UntypedDenseTensor, D]): SubF[cpu.DenseTensor[D, A], cpu.DenseTensor[D, A], cpu.DenseTensor[D, A]] = new CPUSubF(env)

}
