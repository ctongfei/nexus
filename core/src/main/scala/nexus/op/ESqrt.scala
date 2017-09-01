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

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: TypedRealTensorOps[T, R]): ESqrtF[T[A], T[A]] =
    new ESqrtF[T[A], T[A]] {
      import T._
      def gradOps = T.ground[A]
      def forward(x: T[A]) = eSqrt(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| (eInv(y) :* -0.5)
    }

}
