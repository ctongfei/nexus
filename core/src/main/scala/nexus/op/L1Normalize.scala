package nexus.op

import nexus._
import nexus.impl._

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
  implicit def vector[T[_ <: $$], D, A](implicit ops: TypedMathOps[T, D]) = new L1NormalizeF[T[A::$], T[A::$]] {
    import ops._
    def gradOps = ops.ground[A::$]
    def forward(x: T[A::$]) = x :* inv(sum(x))
    def backward(dy: T[A::$], y: T[A::$], x: T[A::$]) = ???
  }
}
