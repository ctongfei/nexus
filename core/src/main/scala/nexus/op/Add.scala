package nexus.op

import nexus._
import nexus.impl._

import scala.annotation._

/**
 * Addition of two tensors of the same axes and shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Add extends PolyDOp2[AddF]

@implicitNotFound("Cannot apply Add to ${X1} and ${X2}.")
trait AddF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Add"
}

object AddF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedMathOps[T, D]): AddF[T[A], T[A], T[A]] =
    new AddF[T[A], T[A], T[A]] {
      def gradOps = ops.ground[A]
      def forward(x1: T[A], x2: T[A]) = x1 + x2
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy
    }

}
