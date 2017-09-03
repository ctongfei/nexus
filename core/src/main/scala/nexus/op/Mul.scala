package nexus.op

import nexus._
import nexus.algebra._

@implicitNotFound("Cannot apply Mul on ${X1} and ${X2}.")
trait MulF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Mul"
}

object MulF {

  implicit def scalar[R](implicit R: RealOps[R]): MulF[R, R, R] =
    new MulF[R, R, R] {
      def gradOps = R
      def backward1(dy: R, y: R, x1: R, x2: R) = R.mul(dy, x2)
      def backward2(dy: R, y: R, x1: R, x2: R) = R.mul(dy, x1)
      def forward(x1: R, x2: R) = R.mul(x1, x2)
    }

  implicit def tensor0dim[T[_ <: $$], R](implicit T: TypedRealTensorOps[T, R]): MulF[T[$], T[$], T[$]] =
    new MulF[T[$], T[$], T[$]] {
      def gradOps = T.ground[$]
      def backward1(dy: T[$], y: T[$], x1: T[$], x2: T[$]) = T.eMul(dy, x2)
      def backward2(dy: T[$], y: T[$], x1: T[$], x2: T[$]) = T.eMul(dy, x1)
      def forward(x1: T[$], x2: T[$]) = T.eMul(x1, x2)
    }

}

@implicitNotFound("Cannot apply EMul to ${X1} and ${X2}.")
trait EMulF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "EMul"
}

object EMulF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit T: TypedRealTensorOps[T, D]): EMulF[T[A], T[A], T[A]] =
    new EMulF[T[A], T[A], T[A]] {
      def gradOps = T.ground[A]
      def forward(x1: T[A], x2: T[A]) = x1 |*| x2
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy |*| x2
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy |*| x1
    }

}
