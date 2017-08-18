package nexus.op

import nexus._
import nexus.impl._

import scala.annotation._

/**
 * L,,2,, distance (Euclidean distance) between two vectors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object L2Distance extends PolyDOp2[L2DistanceF]

@implicitNotFound("Cannot apply L2Distance to ${X1} and ${X2}.")
trait L2DistanceF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "L2Distance"
}

object L2DistanceF {
  
  implicit def vector[T[_ <: $$], D, A](implicit ops: TypedMathOps[T, D]) =
    new L2DistanceF[T[A::$], T[A::$], T[$]] {
      import ops._
      def gradOps = ops.ground[$]
      def forward(x1: T[A::$], x2: T[A::$]) = sqrt(sum(sqr(x1 - x2)))
      def backward1(dy: T[$], y: T[$], x1: T[A::$], x2: T[A::$]) = (x1 - x2) :* (dy |/| y)
      def backward2(dy: T[$], y: T[$], x1: T[A::$], x2: T[A::$]) = (x2 - x1) :* (dy |/| y)
    }
  
}
