package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

trait ESqrF[X, Y] extends DOp1[X, Y] {
  def name = "ESqr"
}

object ESqrF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit T: IsTypedRealTensor[T, D]): ESqrF[T[A], T[A]] =
    new ESqrF[T[A], T[A]] {
      import T._
      def tag = T.ground[A]
      def forward(x: T[A]) = eSqr(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| x :* 2f
    }

}

trait ESqrtF[X, Y] extends DOp1[X, Y] {
  def name = "ESqrt"
}

object ESqrtF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]): ESqrtF[T[A], T[A]] =
    new ESqrtF[T[A], T[A]] {
      import T._
      def tag = T.ground[A]
      def forward(x: T[A]) = eSqrt(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| (eInv(y) :* -0.5)
    }

}
