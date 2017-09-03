package nexus.op

import nexus._
import nexus.algebra._

/**
 * Cosine of a real number.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Cos extends PolyDOp1[CosF]

@implicitNotFound("Cannot apply Cos to ${X}.")
trait CosF[X, Y] extends DOp1[X, Y] {
  def name = "Cos"
}

object CosF {
  implicit def scalar[R](implicit R: RealOps[R]) = new CosF[R, R] {
    import R._
    def gradOps = R
    def backward(dy: R, y: R, x: R) = -dy * sin(x)
    def forward(x: R) = cos(x)
  }
}

/**
 * Element-wise cosine.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ECos extends PolyDOp1[ECosF]

@implicitNotFound("Cannot apply ECos to ${X}.")
trait ECosF[X, Y] extends DOp1[X, Y] {
  def name = "ECos"
}

object ECosF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit T: TypedRealTensorOps[T, D]) = new ECosF[T[A], T[A]] {
    import T._
    def gradOps = T.ground[A]
    def forward(x: T[A]) = eCos(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = -dy |*| eSin(x)
  }

}
