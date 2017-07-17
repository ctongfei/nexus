package nexus.op

import nexus._

/**
 * L,,2,, distance (Euclidean distance) between two vectors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object L2Distance extends PolyOp2[L2DistanceF]

@impMsg("Cannot apply L2Distance to ${X1} and ${X2}.")
trait L2DistanceF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "L2Distance"
}

object L2DistanceF {
  
  implicit def L2DistanceImpl[T[_, _ <: $$], D, A](implicit env: Env[T, D]) =
    new L2DistanceF[T[D, A::$], T[D, A::$], T[D, $]] {
      import env._
      def forward(x1: T[D, A::$], x2: T[D, A::$]) = sqrt(sum(sqr(x1 - x2)))
      def backward1(dy: T[D, $], y: T[D, $], x1: T[D, A::$], x2: T[D, A::$]) = (x1 - x2) :* (dy |/| y)
      def backward2(dy: T[D, $], y: T[D, $], x1: T[D, A::$], x2: T[D, A::$]) = (x2 - x1) :* (dy |/| y)
    }
  
}
