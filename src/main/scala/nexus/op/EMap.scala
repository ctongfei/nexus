package nexus.op

import nexus._

/**
 * Applies an arbitrary differentiable function to all elements in a specific tensor.
 * @note This operation might be slow! Use with caution.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class EMap[D](f: Op1[D, D]) extends ParaPolyOp1[Op1[D, D], EMapF] {
  def parameter = f
}

trait EMapF[P, X, Y] extends (P => Op1[X, Y])

object EMapF {

  implicit def MapImpl[T[_, _ <: $$], D, A <: $$](implicit env: Env[T, D]) = new EMapF[Op1[D, D], T[D, A], T[D, A]] {
    def apply(f: Op1[D, D]) = new Op1[T[D, A], T[D, A]] {
      import env._
      def name = s"EMap[${f.name}]"
      def forward(x: T[D, A]) = map(x)(f.forward)
      def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = zipWith3(dy, y, x)(f.backward)
    }
  }

}
