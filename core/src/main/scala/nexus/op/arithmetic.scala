package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.op.base._

/**
 * Identity function.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Id extends PolyDOp1 {
  implicit def grad[X: Grad]: F[X, X] = new F[X, X] {
    def name = "Id"
    def tag = Grad[X]
    def forward(x: X) = x
    def backward(dy: X, y: X, x: X) = dy
  }
}

/**
 * Adds two scalars or two tensors of the same axes/shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Add extends PolyDOp2 {
  implicit def instance[X: Grad]: F[X, X, X] = new F[X, X, X] {
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
object Sub extends PolyDOp2 {
  implicit def instance[X: Grad]: F[X, X, X] = new F[X, X, X] {
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
object Mul extends PolyDOp2 {

  implicit def scalar[R: IsReal]: F[R, R, R] = new F[R, R, R] {
    def name = "Mul"
    def tag = implicitly[IsReal[R]]
    def forward(x1: R, x2: R) = x1 * x2
    def backward1(dy: R, y: R, x1: R, x2: R) = dy * x2
    def backward2(dy: R, y: R, x1: R, x2: R) = dy * x1
  }

  /**
   * Element-wise multiplication (a.k.a. Hadamard product) between two tensors.
   */
  object Elementwise extends PolyDOp2 {
    implicit def tensor[T[_ <: $$], R, As <: $$](implicit T: IsRealTensor[T, R]): F[T[As], T[As], T[As]] = new F[T[As], T[As], T[As]] {
      def name = "Mul.Elementwise"
      def tag = T.ground[As]
      def forward(x1: T[As], x2: T[As]) = x1 |*| x2
      def backward1(dy: T[As], y: T[As], x1: T[As], x2: T[As]) = dy |*| x2
      def backward2(dy: T[As], y: T[As], x1: T[As], x2: T[As]) = dy |*| x1
    }
  }
}

/**
 * Scalar division.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Div extends TypeInvariantPolyDOp2[IsReal] {
  def name = "Div"
  def forward[R](x1: R, x2: R)(implicit R: IsReal[R]) = x1 / x2
  def backward1[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]) = dy / x2
  def backward2[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]) = -dy * y / x2

  /**
   * Element-wise division between two tensors.
   */
  object Elementwise extends TypeInvariantTensorPolyDOp2[IsRealTensor] {
    def name = "Div.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x1: T[A], x2: T[A])(implicit T: IsRealTensor[T, R]) = x1 |/| x2
    def backward1[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsRealTensor[T, R]) = dy |/| x2
    def backward2[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsRealTensor[T, R]) = -dy |*| y |/| x2
  }
}

/**
 * Negation of any scalar or tensor.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Neg extends TypeInvariantPolyDOp1[Grad] {
  def name = "Neg"
  def forward[R](x: R)(implicit R: Grad[R]) = -x
  def backward[R](dy: R, y: R, x: R)(implicit R: Grad[R]) = -dy
}

/**
 * Scalar multiplicative inverse.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Inv extends TypeInvariantPolyDOp1[IsReal] {
  def name = "Inv"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.inv(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = -dy * R.sqr(y)

  /**
   * Element-wise multiplicative inverse.
   */
  object Elementwise extends TypeInvariantTensorPolyDOp1[IsRealTensor] {
    def name = "Inv.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsRealTensor[T, R]) = T.eInv(x)
    def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensor[T, R]) = -dy |*| T.eSqr(y)
  }
}
