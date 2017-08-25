package nexus.op

import nexus._
import nexus.algebra._

/**
 * Element-wise cosine.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ECos extends PolyDOp1[ECosF]

trait ECosF[X, Y] extends DOp1[X, Y] {
  def name = "ECos"
}

object ECosF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedRealTensorOps[T, D]) = new ECosF[T[A], T[A]] {
    import ops._
    def gradOps = ops.ground[A]
    def forward(x: T[A]) = cos(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = -dy |*| sin(y)
  }

}
