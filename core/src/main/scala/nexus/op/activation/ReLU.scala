package nexus.op.activation

import nexus._

/**
 * Rectified linear unit.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ReLU extends PolyOp1[ReLUF]

trait ReLUF[X, Y] extends Op1[X, Y] {
  def name = "ReLU"
}

object ReLUF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit env: Env[T, D]) = new ReLUF[T[A], T[A]] {
    import env._
    def forward(x: T[A]) = relu(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| pos(x)
  }

}
