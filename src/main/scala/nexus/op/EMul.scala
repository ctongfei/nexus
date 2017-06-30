package nexus.op

import nexus._
import shapeless._

/**
 * Element-wise multiplication (a.k.a. Hadamard product) between two tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object EMul extends GenOp2[EMulF]

trait EMulF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "EMul"
}

object EMulF {

  implicit def EMulImpl[T[D, _ <: HList], D, A <: HList](implicit env: Env[T, D]): EMulF[T[D, A], T[D, A], T[D, A]] =
    new EMulF[T[D, A], T[D, A], T[D, A]] {
      def forward(x1: T[D, A], x2: T[D, A]) = x1 |*| x2
      def backward1(dy: T[D, A], y: T[D, A], x1: T[D, A], x2: T[D, A]) = dy |*| x2
      def backward2(dy: T[D, A], y: T[D, A], x1: T[D, A], x2: T[D, A]) = dy |*| x1
    }

}
