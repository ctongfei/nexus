package nexus.op

import nexus._
import nexus.impl._
import shapeless._

/**
 * Element-wise division between two tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object EDiv extends PolyDOp2[EDivF]

trait EDivF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "EDiv"
}

object EDivF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedMathOps[T, D]): EDivF[T[A], T[A], T[A]] =
    new EDivF[T[A], T[A], T[A]] {
      import ops._
      def gradOps = ops.ground[A]
      def forward(x1: T[A], x2: T[A]) = x1 |/| x2
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy |/| x2
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = -dy |*| x1 |/| sqr(x2)
    }

}
