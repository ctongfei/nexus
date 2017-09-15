package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * @author Tongfei Chen
 */
trait AbsF[X, Y] extends DOp1[X, Y] {
  def name = "Abs"
}

object AbsF {

  implicit def scalar[R](implicit R: IsReal[R]): AbsF[R, R] =
    new AbsF[R, R] {
      def tag = R
      def backward(dy: R, y: R, x: R) = dy * R.sgn(x)
      def forward(x: R) = R.abs(x)
    }

}

trait EAbsF[X, Y] extends DOp1[X, Y] {
  def name = "Abs.Elementwise"
}

object EAbsF {

  implicit def scalar[T[_ <: $$], A <: $$, R](implicit T: IsTypedRealTensor[T, R]): AbsF[T[A], T[A]] =
    new AbsF[T[A], T[A]] {
      def tag = T.ground[A]
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| T.eSgn(x)
      def forward(x: T[A]) = T.eAbs(x)
    }

}

trait L1NormalizeF[X, Y] extends DOp1[X, Y] {
  def name = "L1Normalize"
}

object L1NormalizeF {
  implicit def vector[T[_ <: $$], R, A](implicit T: IsTypedRealTensor[T, R]) = new L1NormalizeF[T[A::$], T[A::$]] {
    import T._
    def tag = T.ground[A::$]
    def forward(x: T[A::$]) = x :* R.inv(sum(x))
    def backward(dy: T[A::$], y: T[A::$], x: T[A::$]) = ???
  }
}
