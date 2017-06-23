package nexus.op

import nexus._
import shapeless._

/**
 * @author Tongfei Chen
 */
object MVMul extends GenOp2[MVMulF]

trait MVMulF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "MVMul"
}

object MVMulF {

  class CPUMVMulF[D, A, B](env: Env[cpu.UntypedDenseTensor, D]) extends MVMulF[cpu.DenseTensor[D, B :: A :: HNil], cpu.DenseTensor[D, A :: HNil], cpu.DenseTensor[D, B :: HNil]] {
    def forward(x1: Input1, x2: Input2) = env.mvMul(x1, x2) typeWith (x1.axes.head :: HNil)
    def backward1(dy: Output, y: Output, x1: Input1, x2: Input2) = env.vvMul(dy, x2) typeWith (dy.axes.head :: x2.axes.head :: HNil)
    def backward2(dy: Output, y: Output, x1: Input1, x2: Input2) = env.mvMul(env.transpose(x1), dy) typeWith (x1.axes.tail.head :: HNil)
  }

  implicit def cpuMVMulF[D, A, B](implicit env: Env[cpu.UntypedDenseTensor, D]): MVMulF[cpu.DenseTensor[D, B :: A :: HNil], cpu.DenseTensor[D, A :: HNil], cpu.DenseTensor[D, B :: HNil]] = new CPUMVMulF(env)

}
