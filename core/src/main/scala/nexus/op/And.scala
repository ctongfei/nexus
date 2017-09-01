package nexus.op

import nexus._
import nexus.algebra._

/**
 * @author Tongfei Chen
 */
object And extends PolyOp2[AndF]

trait AndF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "And"
}

object AndF {

  implicit def scalar[B](implicit B: BoolOps[B]): AndF[B, B, B] =
    new AndF[B, B, B] {
      def forward(x1: B, x2: B) = B.and(x1, x2)
    }

}
