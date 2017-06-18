package nexus.op

import nexus._
import shapeless.HList

/**
 * Subtraction of two tensors of the same axes and shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sub extends GenOp2[SubF]

trait SubF[X1, X2, Y] extends Op2[X1, X2, Y]

object SubF {
  implicit def numeric[T[_, _], D, A <: HList](implicit env: Env[T, D]): SubF[T[D, A], T[D, A], T[D, A]] =
    new SubF[T[D, A], T[D, A], T[D, A]] {
      def forward(x1: T[D, A], x2: T[D, A]) = ??? //x1 - x2
      def backward1(dy: T[D, A], y: T[D, A], x1: T[D, A], x2: T[D, A]) = ??? // dy
      def backward2(dy: T[D, A], y: T[D, A], x1: T[D, A], x2: T[D, A]) = ??? // -dy
    }
}
