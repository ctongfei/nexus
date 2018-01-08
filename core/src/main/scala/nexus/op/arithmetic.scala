package nexus.op

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

  implicit def instance[X]: F[X, X] = new F[X, X] {
    def name = "Id"
    def tag(tx: Type[X]) = tx
    def differentiable = true
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

  implicit def instance[X: Grad]: F[X, X, X] = new F[X, X, X] {
    def name = "Add"
    def tag(tx1: Type[X], tx2: Type[X]) = tx1
    def differentiable = true
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
  implicit def instance[X: Grad]: F[X, X, X] = new F[X, X, X] {
    def name = "Sub"
    def tag(tx1: Type[X], tx2: Type[X]) = tx1
    def differentiable = true
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

  implicit def scalar[R: IsReal]: F[R, R, R] = new F[R, R, R] {
    def name = "Mul"
    def tag(tx1: Type[R], tx2: Type[R]) = tx1
    def differentiable = true
    def forward(x1: R, x2: R) = x1 * x2
    def backward1(dy: R, y: R, x1: R, x2: R) = dy * x2
    def backward2(dy: R, y: R, x1: R, x2: R) = dy * x1
  }

  /**
   * Element-wise multiplication (a.k.a. Hadamard product) between two tensors.
   */
  object Elementwise extends PolyOp2 {
    implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsRealTensorH[T, R]): F[T[A], T[A], T[A]] = new F[T[A], T[A], T[A]] {
      def name = "Mul.Elementwise"
      def tag(tx1: Type[T[A]], tx2: Type[T[A]]) = tx1
      def differentiable = true
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

  implicit def scalar[R: IsReal]: F[R, R, R] = new F[R, R, R] {
    def name = "Div"
    def tag(tx1: Type[R], tx2: Type[R]) = tx1
    def differentiable = true
    def forward(x1: R, x2: R) = x1 / x2
    def backward1(dy: R, y: R, x1: R, x2: R) = dy / x2
    def backward2(dy: R, y: R, x1: R, x2: R) = -dy * y / x2

  }

  /**
   * Element-wise division between two tensors.
   */
  object Elementwise extends PolyOp2 {

    implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsRealTensorH[T, R]): F[T[A], T[A], T[A]] = new F[T[A], T[A], T[A]] {
      def name = "Div.Elementwise"
      def tag(tx1: Type[T[A]], tx2: Type[T[A]]) = tx1
      def differentiable = true
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

  implicit def grad[X: Grad]: F[X, X] = new F[X, X] {
    def name = "Neg"
    def tag(tx: Type[X]) = tx
    def differentiable = true
    def forward(x: X) = -x
    def backward(dy: X, y: X, x: X) = -dy
  }

}

/**
 * Scalar multiplicative inverse.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Inv extends PolyOp1 {

  implicit def scalar[R](implicit R: IsReal[R]): F[R, R] = new F[R, R] {
    def name = "Inv"
    def tag(tx: Type[R]) = tx
    def differentiable = true
    def forward(x: R) = R.inv(x)
    def backward(dy: R, y: R, x: R) = -dy * R.sqr(y)
  }

  /**
   * Element-wise multiplicative inverse.
   */
  object Elementwise extends PolyOp1 {

    implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsRealTensorH[T, R]): F[T[A], T[A]] = new F[T[A], T[A]] {
      def name = "Inv.Elementwise"
      def tag(tx: Type[T[A]]) = tx
      def differentiable = true
      def forward(x: T[A]) = T.eInv(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = -dy |*| T.eSqr(y)
    }

  }
}
