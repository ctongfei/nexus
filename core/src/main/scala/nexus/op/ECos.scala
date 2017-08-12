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

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit env: Env[T, D]) = new ECosF[T[A], T[A]] {
    import env._
    def forward(x: T[A]) = cos(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = -dy |*| sin(y)
  }

}
