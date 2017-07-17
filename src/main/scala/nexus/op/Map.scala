package nexus.op

import nexus._

/**
 * Applies an arbitrary differentiable function to all elements in a specific tensor.
 * @note This operation might be slow! Use with caution.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Map[D](f: Op1[D, D]) extends ArgPolyOp1[Op1[D, D], MapF] {
  def arg = f
}

trait MapF[Arg, X, Y] extends ArgOp1[Arg, X, Y]

object MapF {

  implicit def MapImpl[T[_, _ <: $$], D, A <: $$](implicit env: Env[T, D]) = new MapF[Op1[D, D], T[D, A], T[D, A]] {
    def apply(f: Op1[D, D]) = new Op1[T[D, A], T[D, A]] {
      import env._
      def name = s"Map[${f.name}]"
      def forward(x: T[D, A]) = map(x)(f.forward)
      def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = zipWith3(dy, y, x)(f.backward)
    }
  }

}
