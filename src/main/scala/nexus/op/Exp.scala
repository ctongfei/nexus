package nexus.op

import nexus._

/**
 * Element-wise exponentiation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Exp extends PolyOp1[ExpF]

trait ExpF[X, Y] extends Op1[X, Y] {
  def name = "Exp"
}

object ExpF {

  implicit def ExpImpl[T[_, _ <: $$], D, A <: $$](implicit env: Env[T, D]) = new ExpF[T[D, A], T[D, A]] {
    import env._
    def forward(x: T[D, A]) = exp(x)
    def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = dy |*| y
  }

}
