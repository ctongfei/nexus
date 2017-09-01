package nexus.op

import nexus._
import nexus.algebra._

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
  
  implicit def vector[T[_ <: $$], R, A](implicit T: TypedRealTensorOps[T, R]) =
    new L2DistanceF[T[A::$], T[A::$], R] {
      import T._
      implicit val R = T.R
      def gradOps = T.R
      def forward(x1: T[A::$], x2: T[A::$]) = R.eSqrt(sum(eSqr(x1 - x2)))
      def backward1(dy: R, y: R, x1: T[A::$], x2: T[A::$]) = (x1 - x2) :* (dy / y)
      def backward2(dy: R, y: R, x1: T[A::$], x2: T[A::$]) = (x2 - x1) :* (dy / y)
    }
  
}
