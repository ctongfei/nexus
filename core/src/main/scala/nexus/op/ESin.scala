package nexus.op

import nexus._

/**
 * Element-wise sine.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ESin extends PolyOp1[ESinF]

trait ESinF[X, Y] extends Op1[X, Y] {
  def name = "ESin"
}

object ESinF {

  implicit def tensor[T[_, _ <: $$], D, A <: $$](implicit env: Env[T, D]) = new ESinF[T[D, A], T[D, A]] {
    import env._
    def forward(x: T[D, A]) = sin(x)
    def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = dy |*| cos(y)
  }

}
