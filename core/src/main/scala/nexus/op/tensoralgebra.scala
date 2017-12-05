package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.algebra.typelevel._

import scala.annotation._

/**
 * Scales a tensor by a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Scale extends PolyDOp2 {

  @implicitNotFound("Cannot apply Scale to ${X1} and ${X2}.")
  trait DOp[X1, X2, Y] extends DOp2[X1, X2, Y] {
    def name = "Scale"
  }

  object DOp {

    implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]) = new DOp[R, T[A], T[A]] {
      def tag = T.ground[A]
      def forward(x1: R, x2: T[A]) = x2 :* x1
      def backward1(dy: T[A], y: T[A], x1: R, x2: T[A]) = dy ⋅ x2
      def backward2(dy: T[A], y: T[A], x1: R, x2: T[A]) = dy :* x1
    }

  }
}

/**
 * Inner product of two tensors.
 *
 * Inputs: two tensors 「bb "a"」 and 「bb "b"」 with the same axes and shape.
 *
 * Output: a scalar, computed as 「y = bb"a" cdot bb"b" = sum_(i_1 i_2 ... i_d)  a_(i_1 ... i_d) b_(i_1 ... i_d)」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Dot extends TaTaSPolyDOp2 {
  def name = "Dot"
  def forward[T[_ <: $$], R, As <: $$](x1: T[As], x2: T[As])(implicit T: IsTypedRealTensor[T, R]) = T.dot(x1, x2)
  def backward1[T[_ <: $$], R, As <: $$](dy: R, y: R, x1: T[As], x2: T[As])(implicit T: IsTypedRealTensor[T, R]) = x2 :* dy
  def backward2[T[_ <: $$], R, As <: $$](dy: R, y: R, x1: T[As], x2: T[As])(implicit T: IsTypedRealTensor[T, R]) = x1 :* dy
}

/**
 * Matrix multiplication of two matrices (2-D tensors).
 * @note The second axis of the first operand and the first axis of the second operand must match.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object MatMul extends PolyDOp2 {
  @implicitNotFound("Cannot apply MatMul to ${X1} and ${X2}.")
  trait DOp[X1, X2, Y] extends DOp2[X1, X2, Y] {
    def name = "MatMul"
  }

  object Op {
    implicit def matrix[T[_ <: $$], R, A, B, C](implicit T: IsTypedRealTensor[T, R]): DOp[T[A::B::$], T[B::C::$], T[A::C::$]] =
      new DOp[T[A::B::$], T[B::C::$], T[A::C::$]] {
        import T._
        def tag = T.ground[A::C::$]
        def forward(x1: T[A::B::$], x2: T[B::C::$]) = mmMul(x1, x2)
        def backward1(dy: T[A::C::$], y: T[A::C::$], x1: T[A::B::$], x2: T[B::C::$]) = mmMul(dy, transpose(x2))
        def backward2(dy: T[A::C::$], y: T[A::C::$], x1: T[A::B::$], x2: T[B::C::$]) = mmMul(transpose(x1), dy)
      }
  }
}

/**
 * Transposition of a matrix (2-D tensor).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Transpose extends PolyDOp1 {
  @implicitNotFound("Cannot apply Transpose to ${X}.")
  trait DOp[X, Y] extends DOp1[X, Y] {
    def name = "Transpose"
  }

  object DOp {

    implicit def matrix[T[_ <: $$], R, A, B](implicit T: IsTypedRealTensor[T, R]) = new DOp[T[A::B::$], T[B::A::$]] {
      import T._
      def tag = T.ground[B::A::$]
      def forward(x: T[A::B::$]) = transpose(x)
      def backward(dy: T[B::A::$], y: T[B::A::$], x: T[A::B::$]) = transpose(dy)
    }

  }
}

/**
 * Matrix-vector multiplication.
 *
 * Inputs:
 *  - Matrix \(\mathbf{X}_1\) with axes and shape \((B \to m, A \to n)\).
 *  - Vector \(\mathbf{x}_2\) with axes and shape \((A \to n)\).
 *
 * Output:
 *  - Vector \(\mathbf{y} = \mathbf{X}_1 \mathbf{x}_2\), with shape \((B \to m)\).
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object MVMul extends PolyDOp2 {
  @implicitNotFound("Cannot apply MVMul to ${X1} and ${X2}.")
  trait DOp[X1, X2, Y] extends DOp2[X1, X2, Y] {
    def name = "MVMul"
  }

  object DOp {

    implicit def mv[T[_ <: $$], R, A, B](implicit T: IsTypedRealTensor[T, R]): Op[T[B::A::$], T[A::$], T[B::$]] =
      new DOp[T[B::A::$], T[A::$], T[B::$]] {
        import T._
        def tag = T.ground[B::$]
        def forward(x1: T[B::A::$], x2: T[A::$]) = mvMul(x1, x2)
        def backward1(dy: T[B::$], y: T[B::$], x1: T[B::A::$], x2: T[A::$]) = vvMul(dy, x2)
        def backward2(dy: T[B::$], y: T[B::$], x1: T[B::A::$], x2: T[A::$]) = mvMul(transpose(x1), dy)
      }

  }
}

/**
 * General tensor multiplication (contraction) that marginalizes out all axes between two tensors that match.
 * Einstein summation
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Contract extends PolyDOp2 {
  @implicitNotFound("Cannot apply Contract to ${X1} and ${X2}.")
  trait DOp[X1, X2, Y] extends DOp2[X1, X2, Y] {
    def name = "Contract"
  }

  object DOp {

    implicit def tensor[T[_ <: $$], R, A <: $$, B <: $$, C <: $$](implicit T: IsTypedRealTensor[T, R], sd: SymDiff.Aux[A, B, C]) =
      new DOp[T[A], T[B], T[C]] {
        import T._
        def tag = T.ground[C]
        def forward(x1: T[A], x2: T[B]) = contract(x1, x2)(sd)
        def backward1(dy: T[C], y: T[C], x1: T[A], x2: T[B]) = ??? // contract(dy, x2)(sd.recoverLeft)
        def backward2(dy: T[C], y: T[C], x1: T[A], x2: T[B]) = ??? // contract(dy, x1)(sd.recoverRight)
      }

  }

}
