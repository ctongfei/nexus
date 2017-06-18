package nexus.op

import nexus._
import shapeless._

/**
 * Transposition of a matrix (2-D tensor).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Transpose extends GenOp1[TransposeF]

trait TransposeF[X, Y] extends Op1[X, Y]

object TransposeF {
  implicit def numeric[T[_, _], D, A, B](implicit env: Env[T, D]): TransposeF[T[D, A :: B :: HNil], T[D, B :: A :: HNil]] =
    new TransposeF[T[D, A :: B :: HNil], T[D, B :: A :: HNil]] {
      type X = T[D, A :: B :: HNil]
      type Y = T[D, B :: A :: HNil]
      def forward(x1: X): Y = ???
      def backward(dy: Y, y: Y, x: X) = ??? // dy.transpose
    }
}
