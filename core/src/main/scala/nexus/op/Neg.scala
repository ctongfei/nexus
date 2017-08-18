package nexus.op

import nexus._
import nexus.impl._

/**
 * Negation of any tensor.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Neg extends PolyDOp1[NegF]

@implicitNotFound("Cannot apply Neg to ${X}.")
trait NegF[X, Y] extends DOp1[X, Y] {
  def name = "Neg"
}

object NegF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedMathOps[T, D]): NegF[T[A], T[A]] =
    new NegF[T[A], T[A]] {
      def gradOps = ops.ground[A]
      def forward(x: T[A]) = -x
      def backward(dy: T[A], y: T[A], x: T[A]) = -dy
    }

}
