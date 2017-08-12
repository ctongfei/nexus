package nexus.op

import nexus._

/**
 * Transposition of a matrix (2-D tensor).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Transpose extends PolyOp1[TransposeF]

trait TransposeF[X, Y] extends Op1[X, Y] {
  def name = "Transpose"
}

object TransposeF {

  implicit def matrix[T[_ <: $$], D, A, B](implicit env: Env[T, D]) = new TransposeF[T[A::B::$], T[B::A::$]] {
    import env._
    def forward(x: T[A::B::$]) = transpose(x)
    def backward(dy: T[B::A::$], y: T[B::A::$], x: T[A::B::$]) = transpose(dy)
  }

}
