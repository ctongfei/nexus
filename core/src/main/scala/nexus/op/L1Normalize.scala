package nexus.op

import nexus._
import nexus.algebra._

/**
 * L1 normalization.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object L1Normalize extends PolyDOp1[L1NormalizeF]

trait L1NormalizeF[X, Y] extends DOp1[X, Y] {
  def name = "L1Normalize"
}

object L1NormalizeF {
  implicit def vector[T[_ <: $$], R, A](implicit T: TypedRealTensorOps[T, R]) = new L1NormalizeF[T[A::$], T[A::$]] {
    import T._
    def gradOps = T.ground[A::$]
    def forward(x: T[A::$]) = x :* R.inv(sum(x))
    def backward(dy: T[A::$], y: T[A::$], x: T[A::$]) = ???
  }
}
