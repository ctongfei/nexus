package nexus.op

import nexus._

/**
 * Element-wise exponentiation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object EExp extends PolyOp1[EExpF]

trait EExpF[X, Y] extends Op1[X, Y] {
  def name = "EExp"
}

object EExpF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit env: Env[T, D]) = new EExpF[T[A], T[A]] {
    import env._
    def forward(x: T[A]) = exp(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| y
  }

}
