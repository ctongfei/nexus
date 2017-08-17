package nexus.op

import nexus._
import nexus.impl._

/**
 * Euclidean inner product of two vectors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Dot extends PolyOp2[DotF]

trait DotF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "Dot"
}

object DotF {
  
  implicit def vector[T[_ <: $$], D, A](implicit ops: TypedMathOps[T, D]) = new DotF[T[A::$], T[A::$], T[$]] {
    import ops._
    def _ops = ops.ground[$]
    def forward(x1: T[A::$], x2: T[A::$]) = dot(x1, x2)
    def backward1(dy: T[$], y: T[$], x1: T[A::$], x2: T[A::$]) = x2 :* dy
    def backward2(dy: T[$], y: T[$], x1: T[A::$], x2: T[A::$]) = x1 :* dy
  }
  
}
