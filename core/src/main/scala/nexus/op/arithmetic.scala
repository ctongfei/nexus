package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * Identity function.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Id extends TypeInvariantPolyDOp1[Grad] {
  def name = "Id"
  def forward[R](x: R)(implicit X: Grad[R]) = x
  def backward[R](dy: R, y: R, x: R)(implicit X: Grad[R]) = dy
}

/**
 * Adds two scalars or two scalars of the same axes/shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Add extends TypeInvariantPolyDOp2[Grad] {
  def name = "Add"
  def forward[R](x1: R, x2: R)(implicit R: Grad[R]) = x1 + x2
  def backward1[R](dy: R, y: R, x1: R, x2: R)(implicit R: Grad[R]) = dy
  def backward2[R](dy: R, y: R, x1: R, x2: R)(implicit R: Grad[R]) = dy

  object Elementwise extends TypeInvariantTensorPolyDOp2[IsTypedRealTensor] {
    def name = "Add.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, R]) = x1 + x2
    def backward1[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy
    def backward2[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy
  }
}

/**
 * Subtracts two scalars or two tensors of the same axes/shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sub extends TypeInvariantPolyDOp2[Grad] {
  def name = "Sub"
  def forward[R](x1: R, x2: R)(implicit R: Grad[R]) = x1 - x2
  def backward1[R](dy: R, y: R, x1: R, x2: R)(implicit R: Grad[R]) = dy
  def backward2[R](dy: R, y: R, x1: R, x2: R)(implicit R: Grad[R]) = -dy

  object Elementwise extends TypeInvariantTensorPolyDOp2[IsTypedRealTensor] {
    def name = "Sub.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, R]) = x1 + x2
    def backward1[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy
    def backward2[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, R]) = -dy
  }
}

/**
 * Scalar multiplication.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Mul extends TypeInvariantPolyDOp2[IsReal] {
  def name = "Mul"
  def forward[R](x1: R, x2: R)(implicit R: IsReal[R]) = x1 * x2
  def backward1[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]) = dy * x2
  def backward2[R](dy: R, y: R, x1: R, x2: R)(implicit R: IsReal[R]) = dy * x1

  /**
   * Element-wise multiplication (a.k.a. Hadamard product) between two tensors.
   */
  object Elementwise extends TypeInvariantTensorPolyDOp2[IsTypedRealTensor] {
    def name = "Mul.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, R]) = x1 |*| x2
    def backward1[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy |*| x2
    def backward2[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy |*| x1
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
  object Elementwise extends TypeInvariantTensorPolyDOp2[IsTypedRealTensor] {
    def name = "Div.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, R]) = x1 |/| x2
    def backward1[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy |/| x2
    def backward2[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, R]) = -dy |*| y |/| x2
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
  object Elementwise extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
    def name = "Inv.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, R]) = T.eInv(x)
    def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, R]) = -dy |*| T.eSqr(y)
  }
}
