package nexus.op

import nexus._
import nexus.impl._

/**
 * Transposition of a matrix (2-D tensor).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Transpose extends PolyDOp1[TransposeF]

trait TransposeF[X, Y] extends DOp1[X, Y] {
  def name = "Transpose"
}

object TransposeF {

  implicit def matrix[T[_ <: $$], D, A, B](implicit ops: TypedRealTensorOps[T, D]) = new TransposeF[T[A::B::$], T[B::A::$]] {
    import ops._
    def gradOps = ops.ground[B::A::$]
    def forward(x: T[A::B::$]) = transpose(x)
    def backward(dy: T[B::A::$], y: T[B::A::$], x: T[A::B::$]) = transpose(dy)
  }

}
