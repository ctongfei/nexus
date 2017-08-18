package nexus.op.activation

import nexus._
import nexus.impl._

/**
 * Rectified linear unit.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ReLU extends PolyDOp1[ReLUF]

trait ReLUF[X, Y] extends DOp1[X, Y] {
  def name = "ReLU"
}

object ReLUF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedMathOps[T, D]) = new ReLUF[T[A], T[A]] {
    import ops._
    def gradOps = ops.ground[A]
    def forward(x: T[A]) = relu(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| pos(x)
  }

}
