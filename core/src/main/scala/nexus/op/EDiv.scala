package nexus.op

import nexus._
import shapeless._

/**
 * Element-wise division between two tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object EDiv extends PolyOp2[EDivF]

trait EDivF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "EDiv"
}

object EDivF {

  implicit def EDivImpl[T[D, _ <: HList], D, A <: HList](implicit env: Env[T, D]): EDivF[T[D, A], T[D, A], T[D, A]] =
    new EDivF[T[D, A], T[D, A], T[D, A]] {
      import env._
      def forward(x1: T[D, A], x2: T[D, A]) = x1 |/| x2
      def backward1(dy: T[D, A], y: T[D, A], x1: T[D, A], x2: T[D, A]) = dy |/| x2
      def backward2(dy: T[D, A], y: T[D, A], x1: T[D, A], x2: T[D, A]) = -dy |*| x1 |/| sqr(x2)
    }

}
