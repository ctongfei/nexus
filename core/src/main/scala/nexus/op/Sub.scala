package nexus.op

import nexus._
import nexus.algebra._

@implicitNotFound("Cannot apply Sub to ${X1} and ${X2}.")
trait SubF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Sub"
}

object SubF {

  implicit def scalar[R](implicit R: RealOps[R]): SubF[R, R, R] =
    new SubF[R, R, R] {
      def gradOps = R
      def backward1(dy: R, y: R, x1: R, x2: R) = dy
      def backward2(dy: R, y: R, x1: R, x2: R) = -dy
      def forward(x1: R, x2: R) = x1 - x2
    }

  implicit def tensor[T[A <: $$], R, A <: $$](implicit T: TypedRealTensorOps[T, R]) = new SubF[T[A], T[A], T[A]] {
    def gradOps = T.ground[A]
    def forward(x1: T[A], x2: T[A]): T[A] = x1 - x2
    def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]): T[A] = dy
    def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]): T[A] = -dy
  }

}
