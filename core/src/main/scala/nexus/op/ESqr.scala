package nexus.op

import nexus._
import nexus.algebra._

/**
 * Element-wise square.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ESqr extends PolyDOp1[ESqrF]

trait ESqrF[X, Y] extends DOp1[X, Y] {
  def name = "ESqr"
}

object ESqrF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit T: TypedRealTensorOps[T, D]): ESqrF[T[A], T[A]] =
    new ESqrF[T[A], T[A]] {
      import T._
      def gradOps = T.ground[A]
      def forward(x: T[A]) = eSqr(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| x :* 2f
    }

}
