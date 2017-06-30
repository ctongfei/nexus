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

  implicit def TransposeImpl[T[D, _ <: HList], D, A, B](implicit env: Env[T, D]) = new TransposeF[T[D, A::B::$], T[D, B::A::$]] {
    import env._
    def forward(x: T[D, A::B::$]) = transpose(x)
    def backward(dy: T[D, B::A::$], y: T[D, B::A::$], x: T[D, A::B::$]) = transpose(dy)
  }

}
