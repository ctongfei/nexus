package nexus.op

import nexus._

/**
 * Element-wise square.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ESqr extends PolyOp1[ESqrF]

trait ESqrF[X, Y] extends Op1[X, Y] {
  def name = "ESqr"
}

object ESqrF {

  implicit def ESqrImpl[T[_, _ <: $$], D, A <: $$](implicit env: Env[T, D]) = new ESqrF[T[D, A], T[D, A]] {
    import env._
    def forward(x: T[D, A]) = sqr(x)
    def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = dy |*| x :* 2f
  }

}
