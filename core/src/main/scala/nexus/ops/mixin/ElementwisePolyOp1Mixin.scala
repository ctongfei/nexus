package nexus.ops.mixin

import nexus.PolyOp1
import nexus.exception._


trait RealElementwisePolyOp1Mixin { poly: PolyOp1 =>

  def name: String

  def forwardR[R](x: R)(implicit R: IsReal[R]): R
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]): R

  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]): T[A]
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]): T[A]

  implicit def fR[R](implicit R: IsReal[R]): F[R, R] =
    new F[R, R] {
      type Tag[e] = IsReal[e]
      def name = poly.name
      def tag = R
      def forward(x: R) = poly.forwardR(x)
      def backward(dy: R, y: R, x: R) = poly.backwardR(dy, y, x)
    }

  implicit def fTR[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] =
    new F[T[A], T[A]] {
      type Tag[ta] = IsTensor[T[A], R]
      def name = s"${poly.name}.Elementwise"
      def tag = T.ground[A]
      def forward(x: T[A]) = poly.forwardTR(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = poly.backwardTR(dy, y, x)
    }

}

trait IntElementwisePolyOp1Mixin { poly: PolyOp1 =>

  def name: String

  def forwardZ[Z](x: Z)(implicit Z: IsInt[Z]): Z
  def forwardTZ[T[_], Z, A](x: T[A])(implicit T: IsIntTensorK[T, Z]): T[A]

  implicit def fZ[Z](implicit Z: IsInt[Z]): F[Z, Z] =
    new F[Z, Z] {
      type Tag[e] = IsInt[e]
      def name = poly.name
      def tag = Z
      override def differentiable = false
      def forward(x: Z) = poly.forwardZ(x)
      def backward(dy: Z, y: Z, x: Z) = throw new OperatorNotDifferentiableException(this, 1)
    }

  implicit def fTZ[T[_], Z, A](implicit T: IsIntTensorK[T, Z]): F[T[A], T[A]] =
    new F[T[A], T[A]] {
      type Tag[ta] = IsTensor[T[A], Z]
      def name = s"${poly.name}.Elementwise"
      def tag = T.ground[A]
      override def differentiable = false
      def forward(x: T[A]) = poly.forwardTZ(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = throw new OperatorNotDifferentiableException(this, 1)
    }

}


trait BoolElementwisePolyOp1Mixin { poly: PolyOp1 =>

  def name: String

  def forwardB[B](x: B)(implicit B: IsBool[B]): B
  def forwardTB[T[_], B, A](x: T[A])(implicit T: IsBoolTensorK[T, B]): T[A]

  implicit def fB[B](implicit B: IsBool[B]): F[B, B] =
    new F[B, B] {
      type Tag[b] = IsBool[b]
      def name = poly.name
      def tag = B
      override def differentiable = false
      def forward(x: B) = poly.forwardB(x)
      def backward(dy: B, y: B, x: B) = throw new OperatorNotDifferentiableException(this, 1)
    }

  implicit def fTB[T[_], B, A](implicit T: IsBoolTensorK[T, B]): F[T[A], T[A]] =
    new F[T[A], T[A]] {
      type Tag[ta] = IsTensor[T[A], B]
      def name = s"${poly.name}.Elementwise"
      def tag = T.ground[A]
      override def differentiable = false
      def forward(x: T[A]) = poly.forwardTB(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = throw new OperatorNotDifferentiableException(this, 1)
    }

}
