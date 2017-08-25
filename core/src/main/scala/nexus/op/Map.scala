package nexus.op

import nexus._
import nexus.algebra._

/**
 * Applies an arbitrary differentiable function to all elements in a specific tensor.
 * @note This operation might be slow! Use with caution.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Map[D](f: DOp1[D, D]) extends ParaPolyDOp1[DOp1[D, D], MapF] {
  def parameter = f
}

trait MapF[P, X, Y] extends (P => DOp1[X, Y])

object MapF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedRealTensorOps[T, D]) = new MapF[DOp1[D, D], T[A], T[A]] {
    def apply(f: DOp1[D, D]) = new DOp1[T[A], T[A]] {
      import ops._
      def name = s"EMap[${f.name}]"
      def gradOps = ops.ground[A]
      def forward(x: T[A]) = map(x)(f.forward)
      def backward(dy: T[A], y: T[A], x: T[A]) = map3(dy, y, x)(f.backward)
    }
  }

}
