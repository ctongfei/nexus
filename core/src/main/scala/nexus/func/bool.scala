package nexus.func

import nexus._
import nexus.algebra._


trait AndF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "And"
}

object AndF {

  implicit def bool[B](implicit B: IsBool[B]): AndF[B, B, B] =
    new AndF[B, B, B] {
      def forward(x1: B, x2: B) = B.and(x1, x2)
    }

}
