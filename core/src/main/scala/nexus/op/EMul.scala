package nexus.op

import nexus._
import nexus.impl._
import shapeless._

/**
 * Element-wise multiplication (a.k.a. Hadamard product) between two tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object EMul extends PolyDOp2[EMulF]

trait EMulF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "EMul"
}

object EMulF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedMathOps[T, D]): EMulF[T[A], T[A], T[A]] =
    new EMulF[T[A], T[A], T[A]] {
      def gradOps = ops.ground[A]
      def forward(x1: T[A], x2: T[A]) = x1 |*| x2
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy |*| x2
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy |*| x1
    }

}
