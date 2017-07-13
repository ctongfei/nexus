package nexus.op

import nexus._

/**
 * Element-wise square.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sqr extends PolyOp1[SqrF]

trait SqrF[X, Y] extends Op1[X, Y] {
  def name = "Sqr"
}

object SqrF {

  implicit def SqrImpl[T[_, _ <: $$], D, A <: $$](implicit env: Env[T, D]) = new SqrF[T[D, A], T[D, A]] {
    import env._
    def forward(x: T[D, A]) = sqr(x)
    def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = dy |*| x :* 2f
  }

}
