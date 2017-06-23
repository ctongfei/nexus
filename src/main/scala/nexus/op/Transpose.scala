package nexus.op

import nexus._
import nexus.cpu.DenseTensor
import nexus.util._
import shapeless._

/**
 * Transposition of a matrix (2-D tensor).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Transpose extends GenOp1[TransposeF]

trait TransposeF[X, Y] extends Op1[X, Y] {
  def name = "Transpose"
}

object TransposeF {
  class CPUTransposeF[D, A, B](env: Env[cpu.UntypedDenseTensor, D]) extends TransposeF[cpu.DenseTensor[D, A :: B :: HNil], cpu.DenseTensor[D, B :: A :: HNil]] {
    def forward(x: Input) = env.transpose(x) typeWith AxesUtils.swap(x.axes)
    def backward(dy: Output, y: Output, x: Input) = env.transpose(dy) typeWith x.axes
  }

  implicit def cpuTransposeF[D, A, B](implicit env: Env[cpu.UntypedDenseTensor, D]): TransposeF[cpu.DenseTensor[D, A :: B :: HNil], cpu.DenseTensor[D, B :: A :: HNil]] = new CPUTransposeF(env)
}
