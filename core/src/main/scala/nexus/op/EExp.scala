package nexus.op

import nexus._

/**
 * Element-wise exponentiation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object EExp extends PolyOp1[EExpF]

trait EExpF[X, Y] extends Op1[X, Y] {
  def name = "Exp"
}

object EExpF {

  implicit def ExpImpl[T[_, _ <: $$], D, A <: $$](implicit env: Env[T, D]) = new EExpF[T[D, A], T[D, A]] {
    import env._
    def forward(x: T[D, A]) = exp(x)
    def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = dy |*| y
  }

}
