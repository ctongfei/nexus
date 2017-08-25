package nexus.op

import nexus._
import nexus.algebra._

/**
 * Subtraction of two tensors of the same axes and shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sub extends PolyDOp2[SubF]

trait SubF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Sub"
}

object SubF {

  implicit def tensor[T[A <: $$], D, A <: $$](implicit ops: TypedRealTensorOps[T, D]) = new SubF[T[A], T[A], T[A]] {
    def gradOps = ops.ground[A]
    def forward(x1: T[A], x2: T[A]): T[A] = x1 - x2
    def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]): T[A] = dy
    def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]): T[A] = -dy
  }

}
