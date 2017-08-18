package nexus.op

import nexus._
import nexus.impl._

/**
 * Element-wise sine.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ESin extends PolyDOp1[ESinF]

trait ESinF[X, Y] extends DOp1[X, Y] {
  def name = "ESin"
}

object ESinF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedMathOps[T, D]) = new ESinF[T[A], T[A]] {
    import ops._
    def gradOps = ops.ground[A]
    def forward(x: T[A]) = sin(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| cos(y)
  }

}
