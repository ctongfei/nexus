package nexus.op

import nexus._
import nexus.algebra._

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

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: TypedRealTensorOps[T, R]) = new ESinF[T[A], T[A]] {
    import T._
    def gradOps = T.ground[A]
    def forward(x: T[A]) = eSin(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| eCos(y)
  }

}
