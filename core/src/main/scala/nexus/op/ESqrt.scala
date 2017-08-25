package nexus.op

import nexus._
import nexus.algebra._

/**
 * Element-wise square root.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ESqrt extends PolyDOp1[ESqrtF]

trait ESqrtF[X, Y] extends DOp1[X, Y] {
   def name = "ESqrt"
}

object ESqrtF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit ops: TypedRealTensorOps[T, R]): ESqrtF[T[A], T[A]] =
    new ESqrtF[T[A], T[A]] {
      import ops._
      def gradOps = ops.ground[A]
      def forward(x: T[A]) = sqrt(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| (inv(y) :* -0.5)
    }

}
