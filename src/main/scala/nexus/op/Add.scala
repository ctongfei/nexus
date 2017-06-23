package nexus.op

import nexus._
import shapeless._

/**
 * Addition of two tensors of the same axes and shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Add extends GenOp2[AddF]

trait AddF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "Add"
}

object AddF {

  class CPUAddF[D, A <: HList](implicit env: Env[cpu.UntypedDenseTensor, D]) extends AddF[cpu.DenseTensor[D, A], cpu.DenseTensor[D, A], cpu.DenseTensor[D, A]] {
    import cpu._
    def forward(x1: DenseTensor[D, A], x2: DenseTensor[D, A]) = (x1 + x2) typeWith x1.axes
    def backward1(dy: DenseTensor[D, A], y: DenseTensor[D, A], x1: DenseTensor[D, A], x2: DenseTensor[D, A]) = dy
    def backward2(dy: DenseTensor[D, A], y: DenseTensor[D, A], x1: DenseTensor[D, A], x2: DenseTensor[D, A]) = dy
  }

  implicit def cpuAddF[D, A <: HList](implicit env: Env[cpu.UntypedDenseTensor, D]): AddF[cpu.DenseTensor[D, A], cpu.DenseTensor[D, A], cpu.DenseTensor[D, A]] = new CPUAddF
}

