package nexus.op

import nexus._
import nexus.impl._

/**
 * Element-wise square.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ESqr extends PolyOp1[ESqrF]

trait ESqrF[X, Y] extends Op1[X, Y] {
  def name = "ESqr"
}

object ESqrF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedMathOps[T, D]) = new ESqrF[T[A], T[A]] {
    import ops._
    def _ops = ops.ground[A]
    def forward(x: T[A]) = sqr(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| x :* 2f
  }

}
