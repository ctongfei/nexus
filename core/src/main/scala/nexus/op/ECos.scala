package nexus.op

import nexus._

/**
 * Element-wise cosine.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ECos extends PolyOp1[ECosF]

trait ECosF[X, Y] extends Op1[X, Y] {
  def name = "ECos"
}

object ECosF {

  implicit def tensor[T[_, _ <: $$], D, A <: $$](implicit env: Env[T, D]) = new ECosF[T[D, A], T[D, A]] {
    import env._
    def forward(x: T[D, A]) = cos(x)
    def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = -dy |*| sin(y)
  }

}
