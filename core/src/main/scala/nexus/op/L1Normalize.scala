package nexus.op

import nexus._

/**
 * L1 normalization.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object L1Normalize extends PolyOp1[L1NormalizeF]

trait L1NormalizeF[X, Y] extends Op1[X, Y] {
  def name = "L1Normalize"
}

object L1NormalizeF {
  implicit def L1NormalizeImpl[T[_, _ <: $$], D, A](implicit env: Env[T, D]) = new L1NormalizeF[T[D, A::$], T[D, A::$]] {
    import env._
    def forward(x: T[D, A::$]) = {
      val s = sum(x)
      scale(x, getScalar(untype(inv(s))))
    }
    def backward(dy: T[D, A::$], y: T[D, A::$], x: T[D, A::$]) = ???
  }
}
