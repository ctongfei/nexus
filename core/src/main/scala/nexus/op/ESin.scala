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

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit env: Env[T, D]) = new ESinF[T[A], T[A]] {
    import env._
    def forward(x: T[A]) = sin(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| cos(y)
  }

}
