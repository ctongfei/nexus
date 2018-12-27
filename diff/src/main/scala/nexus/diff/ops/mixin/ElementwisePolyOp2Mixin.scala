package nexus.diff.ops.mixin

import nexus.diff._
import nexus.diff.exception._
import nexus._


trait RealElementwisePolyOp2Mixin { poly: PolyOp2 =>

  def name: String

  def forwardR[R](x1: R, x2: R)(implicit R: IsReal[R]): R
  def backward1R[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]): R
  def backward2R[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]): R

  def forwardTR[T[_], R, A](x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]): T[A]
  def backward1TR[T[_], R, A](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]): T[A]
  def backward2TR[T[_], R, A](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsRealTensorK[T, R]): T[A]

  implicit def fR[R](implicit R: IsReal[R]): F[R, R, R] =
    new F[R, R, R] {
      def name = poly.name
      def tag = Tag.real[R]
      def forward(x1: R, x2: R) = poly.forwardR(x1, x2)
      def backward1(dy: R, y: R, x1: R, x2: R) = poly.backward1R(dy, y, x1, x2)
      def backward2(dy: R, y: R, x1: R, x2: R) = poly.backward2R(dy, y, x1, x2)
    }

  implicit def fTR[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A], T[A]] =
    new F[T[A], T[A], T[A]] {
      def name = s"${poly.name}.Elementwise"
      def tag = Tag.realTensor[T, R, A]
      def forward(x1: T[A], x2: T[A]) = poly.forwardTR(x1, x2)
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = poly.backward1TR(dy, y, x1, x2)
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = poly.backward2TR(dy, y, x1, x2)
    }

}

trait IntElementwisePolyOp2Mixin { poly: PolyOp2 =>

  def name: String

  def forwardZ[Z](x1: Z, x2: Z)(implicit Z: IsInt[Z]): Z

  def forwardTZ[T[_], Z, A](x1: T[A], x2: T[A])(implicit T: IsIntTensorK[T, Z]): T[A]

  implicit def fZ[Z](implicit Z: IsInt[Z]): F[Z, Z, Z] =
    new F[Z, Z, Z] {
      def name = poly.name
      def tag = Tag.int[Z]
      override def differentiable = false
      def forward(x1: Z, x2: Z) = poly.forwardZ(x1, x2)
      def backward1(dy: Z, y: Z, x1: Z, x2: Z) = throw new OperatorNotDifferentiableException(this, 1)
      def backward2(dy: Z, y: Z, x1: Z, x2: Z) = throw new OperatorNotDifferentiableException(this, 2)
    }

  implicit def fTZ[T[_], Z, A](implicit T: IsIntTensorK[T, Z]): F[T[A], T[A], T[A]] =
    new F[T[A], T[A], T[A]] {
      def name = s"${poly.name}.Elementwise"
      def tag = ???
      def forward(x1: T[A], x2: T[A]) = poly.forwardTZ(x1, x2)
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = throw new OperatorNotDifferentiableException(this, 1)
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = throw new OperatorNotDifferentiableException(this, 1)
    }

}

trait BoolElementwisePolyOp2Mixin { poly: PolyOp2 =>

  def name: String

  def forwardB[B](x1: B, x2: B)(implicit B: IsBool[B]): B

  def forwardTB[T[_], B, A](x1: T[A], x2: T[A])(implicit T: IsBoolTensorK[T, B]): T[A]

  implicit def fB[B](implicit B: IsBool[B]): F[B, B, B] =
    new F[B, B, B] {
      def name = poly.name
      def tag = Tag.bool[B]
      override def differentiable = false
      def forward(x1: B, x2: B) = poly.forwardB(x1, x2)
      def backward1(dy: B, y: B, x1: B, x2: B) = throw new OperatorNotDifferentiableException(this, 1)
      def backward2(dy: B, y: B, x1: B, x2: B) = throw new OperatorNotDifferentiableException(this, 2)
    }

  implicit def fTB[T[_], B, A](implicit T: IsBoolTensorK[T, B]): F[T[A], T[A], T[A]] =
    new F[T[A], T[A], T[A]] {
      def name = s"${poly.name}.Elementwise"
      def tag = ???
      def forward(x1: T[A], x2: T[A]) = poly.forwardTB(x1, x2)
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = throw new OperatorNotDifferentiableException(this, 1)
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = throw new OperatorNotDifferentiableException(this, 1)
    }

}
