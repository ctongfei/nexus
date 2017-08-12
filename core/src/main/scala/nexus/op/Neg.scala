package nexus.op

import nexus._

/**
 * Negation of any tensor.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Neg extends PolyOp1[NegF]

@impMsg("Cannot apply Neg to ${X}.")
trait NegF[X, Y] extends Op1[X, Y] {
  def name = "Neg"
}

object NegF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit env: Env[T, D]): NegF[T[A], T[A]] =
    new NegF[T[A], T[A]] {
      def forward(x: T[A]) = -x
      def backward(dy: T[A], y: T[A], x: T[A]) = -dy
    }

}
