package nexus.func

import nexus._
import nexus.algebra._

trait DropoutF[P, X, Y] extends (P => DOp1[X, Y])

object DropoutF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit T: IsTypedRealTensor[T, D]) = new DropoutF[Double, T[A], T[A]] {
    def apply(rate: Double) = new DOp1[T[A], T[A]] {
      def name = s"Dropout[$rate]"
      def tag = T.ground[A]
      def forward(x: T[A]) = x // TODO: Not implemented!
      def backward(dy: T[A], y: T[A], x: T[A]) = dy // as if it doesn't exist
    }
  }

}
