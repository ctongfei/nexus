package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

@implicitNotFound("Cannot apply CrossEntropy to ${P} and ${Q}.")
trait CrossEntropyF[P, Q, Y] extends DOp2[P, Q, Y] {
  def name = "CrossEntropy"
}

object CrossEntropyF {

  implicit def vector[T[_ <: $$], R, A](implicit T: IsTypedRealTensor[T, R]): CrossEntropyF[T[A::$], T[A::$], R] =
    new CrossEntropyF[T[A :: $], T[A :: $], R] {
      import T._
      implicit val R = H.R
      def tag = H.R
      def forward(p: T[A :: $], q: T[A :: $]) =
        -(sum(p |*| eLog(q)))
      def backward1(dl: R, l: R, p: T[A :: $], q: T[A :: $]) =
        -eLog(q) :* dl
      def backward2(dl: R, l: R, p: T[A :: $], q: T[A :: $]) =
        -(p |/| q) :* dl
    }

}

trait KullbackLeiblerDivergenceF[P, Q, Y] extends DOp2[P, Q, Y] {
  def name = "KullbackLeiblerDivergence"
}

object KullbackLeiblerDivergenceF {

  implicit def vector[T[_ <: $$], R, A](implicit T: IsTypedRealTensor[T, R]): KullbackLeiblerDivergenceF[T[A::$], T[A::$], R] =
    new KullbackLeiblerDivergenceF[T[A::$], T[A::$], R] {
      import T._
      def tag = H.R
      def forward(p: T[A::$], q: T[A::$]) =
        sum(p |*| eLog(p |/| q))
      def backward1(dy: R, y: R, p: T[A::$], q: T[A::$]) = ???
      def backward2(dy: R, y: R, p: T[A::$], q: T[A::$]) = ???
    }

}
