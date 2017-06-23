package nexus.op.activation

import nexus._
import nexus.cpu.DenseTensor
import nexus.op._
import shapeless._

/**
 * Sigmoid activation function that maps any real output to the interval (0, 1).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sigmoid extends GenOp1[SigmoidF]

trait SigmoidF[X, Y] extends Op1[X, Y] {
  def name = "Sigmoid"
}

object SigmoidF {

  class CPUSigmoidF[D, A <: HList](env: Env[cpu.UntypedDenseTensor, D]) extends NegF[cpu.DenseTensor[D, A], cpu.DenseTensor[D, A]] {
    def forward(x: DenseTensor[D, A]) = env.sigmoid(x) typeWith x.axes
    def backward(dy: DenseTensor[D, A], y: DenseTensor[D, A], x: DenseTensor[D, A]) = {
      env.mul(dy, env.mul(y, env.addS(env.neg(y), env.one))) typeWith x.axes
    }
  }

  implicit def cpuSigmoidF[D, A <: HList](implicit env: Env[cpu.UntypedDenseTensor, D]): SigmoidF[cpu.DenseTensor[D, A], cpu.DenseTensor[D, A]] = new CPUSigmoidF(env)

}
