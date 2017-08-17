package nexus.op

import nexus._
import nexus.impl._

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

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedMathOps[T, D]) = new EExpF[T[A], T[A]] {
    import ops._
    def _ops = ops.ground[A]
    def forward(x: T[A]) = exp(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| y
  }

}
