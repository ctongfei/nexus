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

  implicit def ReLUImpl[T[_, _ <: $$], D, A <: $$](implicit env: Env[T, D]) = new ReLUF[T[D, A], T[D, A]] {
    import env._
    def forward(x: T[D, A]) = relu(x)
    def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = dy |*| pos(x)
  }

}
