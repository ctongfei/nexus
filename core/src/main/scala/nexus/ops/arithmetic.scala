package nexus.ops

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.exception._

/**
 * Identity function.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Id extends PolyOp1 {

  implicit def idF[X]: F[X, X] =
    new F[X, X] {
      type Tag[x] = AnyType[x]
      def name = "Id"
      def tag = AnyType[X]
      def forward(x: X) = x
      def backward(dy: X, y: X, x: X) = dy
    }

}

/**
 * Adds two scalars or two tensors of the same axes/shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Add extends PolyOp2 {

  implicit def addF[X: Grad]: F[X, X, X] =
    new F[X, X, X] {
      type Tag[x] = Grad[x]
      def name = "Add"
      def tag = Grad[X]
      def forward(x1: X, x2: X): X = x1 + x2
      def backward1(dy: X, y: X, x1: X, x2: X): X = dy
      def backward2(dy: X, y: X, x1: X, x2: X): X = dy
    }

}

/**
 * Subtracts two scalars or two tensors of the same axes/shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sub extends PolyOp2 {
  implicit def subF[X: Grad]: F[X, X, X] =
    new F[X, X, X] {
      type Tag[x] = Grad[x]
      def name = "Sub"
      def tag = Grad[X]
      def forward(x1: X, x2: X) = x1 - x2
      def backward1(dy: X, y: X, x1: X, x2: X) = dy
      def backward2(dy: X, y: X, x1: X, x2: X) = -dy
    }
}

/**
 * Scalar multiplication.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Mul extends PolyOp2 {

  implicit def mulF[R](implicit R: IsReal[R]): F[R, R, R] =
    new F[R, R, R] {
      type Tag[x] = IsReal[x]
      def name = "Mul"
      def tag = R
      def forward(x1: R, x2: R) = x1 * x2
      def backward1(dy: R, y: R, x1: R, x2: R) = dy * x2
      def backward2(dy: R, y: R, x1: R, x2: R) = dy * x1
    }

  /**
   * Element-wise multiplication (a.k.a. Hadamard product) between two tensors.
   */
  object Elementwise extends PolyOp2 {
    implicit def mulElementwiseF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A], T[A]] =
      new F[T[A], T[A], T[A]] {
        type Tag[t] = IsRealTensor[t, R]
        def name = "Mul.Elementwise"
        def tag = T.ground[A]
        def forward(x1: T[A], x2: T[A]) = x1 |*| x2
        def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy |*| x2
        def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy |*| x1
      }
  }
}

/**
 * Scalar division.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Div extends PolyOp2 {

  implicit def divF[R: IsReal]: F[R, R, R] =
    new F[R, R, R] {
      type Tag[r] = IsReal[r]
      def name = "Div"
      def tag = IsReal[R]
      def forward(x1: R, x2: R) = x1 / x2
      def backward1(dy: R, y: R, x1: R, x2: R) = dy / x2
      def backward2(dy: R, y: R, x1: R, x2: R) = -dy * y / x2
    }

  /**
   * Element-wise division between two tensors.
   */
  object Elementwise extends PolyOp2 {

    implicit def divElementwiseF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A], T[A]] = new F[T[A], T[A], T[A]] {
      type Tag[t] = IsRealTensor[t, R]
      def name = "Div.Elementwise"
      def tag = T.ground[A]
      def forward(x1: T[A], x2: T[A]) = x1 |/| x2
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy |/| x2
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = -dy |*| y |/| x2
    }

  }
}

/**
 * Negation of any scalar or tensor.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Neg extends PolyOp1 {

  implicit def negF[X: Grad]: F[X, X] =
    new F[X, X] {
      type Tag[x] = Grad[x]
      def name = "Neg"
      def tag = Grad[X]
      def forward(x: X) = -x
      def backward(dy: X, y: X, x: X) = -dy
  }

}

/**
 * Scalar multiplicative inverse.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Inv extends PolyElementwiseOp1[IsReal, IsRealTensorK] {
  def name = "Inv"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.inv(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = -dy * R.sqr(y)
  def forwardElementwise[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.eInv(x)
  def backwardElementwise[T[_], E, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, E]) = -dy |*| T.eSqr(y)
}
