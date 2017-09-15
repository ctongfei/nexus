package nexus.func

import nexus._
import nexus.algebra._

@implicitNotFound("Cannot apply Map[${P}] to ${X}.")
trait MapF[P, X, Y] extends (P => DOp1[X, Y])

object MapF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]) = new MapF[DOp1[R, R], T[A], T[A]] {
    def apply(f: DOp1[R, R]) = new DOp1[T[A], T[A]] {
      import T._
      def name = s"Map[${f.name}]"
      def tag = T.ground[A]
      def forward(x: T[A]) = map(x)(f.forward)
      def backward(dy: T[A], y: T[A], x: T[A]) = map3(dy, y, x)(f.backward)
    }
  }

}
