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

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit T: TypedRealTensorOps[T, D]) = new ECosF[T[A], T[A]] {
    import T._
    def gradOps = T.ground[A]
    def forward(x: T[A]) = eCos(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = -dy |*| eSin(y)
  }

}
