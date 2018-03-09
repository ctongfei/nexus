package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.algebra.typelevel._
/**
 * Scales a tensor by a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Scale extends PolyOp2 {

  implicit def scaleF[T[_], R, A](implicit T: IsRealTensorK[T, R]) = new F[R, T[A], T[A]] {
    def name = "Scale"
    def tag(tx1: Type[R], tx2: Type[T[A]]) = tx2
    def forward(x1: R, x2: T[A]) = x2 :* x1
    def backward1(dy: T[A], y: T[A], x1: R, x2: T[A]) = dy ⋅ x2
    def backward2(dy: T[A], y: T[A], x1: R, x2: T[A]) = dy :* x1
  }

  val By = Curried1

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
object Dot extends PolyOp2 {
  implicit def dotF[T[_], R, A](implicit T: IsRealTensorK[T, R]) = new F[T[A], T[A], R] {
    def name = "Dot"
    def tag(tx1: Type[T[A]], tx2: Type[T[A]]) = T.R
    def forward(x1: T[A], x2: T[A]) = T.dot(x1, x2)
    def backward1(dy: R, y: R, x1: T[A], x2: T[A]) = x2 :* dy
    def backward2(dy: R, y: R, x1: T[A], x2: T[A]) = x1 :* dy
  }
}

/**
 * Matrix multiplication of two matrices (2-D tensors).
 * @note The second axis of the first operand and the first axis of the second operand must match.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object MatMul extends PolyOp2 {

  implicit def matMulF[T[_], R, A: Label, B: Label, C: Label]
  (implicit T: IsRealTensorK[T, R]): F[T[(A, B)], T[(B, C)], T[(A, C)]] =
    new F[T[(A, B)], T[(B, C)], T[(A, C)]] {
      import T._
      def name = "MatMul"
      def tag(tx1: Type[T[(A, B)]], tx2: Type[T[(B, C)]]) = T.ground[(A, C)]
      def forward(x1: T[(A, B)], x2: T[(B, C)]) = mmMul(x1, x2)
      def backward1(dy: T[(A, C)], y: T[(A, C)], x1: T[(A, B)], x2: T[(B, C)]) = mmMul(dy, transpose(x2))
      def backward2(dy: T[(A, C)], y: T[(A, C)], x1: T[(A, B)], x2: T[(B, C)]) = mmMul(transpose(x1), dy)
    }
}

/**
 * Transposition of a matrix (2-D tensor).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Transpose extends PolyOp1 {
  implicit def transposeF[T[_], R, A: Label, B: Label](implicit T: IsRealTensorK[T, R]) = new F[T[(A, B)], T[(B, A)]] {
    def name = "Transpose"
    def tag(tx: Type[T[(A, B)]]) = T.ground[(B, A)]
    def forward(x: T[(A, B)]) = T.transpose(x)
    def backward(dy: T[(B, A)], y: T[(B, A)], x: T[(A, B)]) = T.transpose(dy)
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
object MVMul extends PolyOp2 {

  implicit def mvMulF[T[_], R, A: Label, B: Label](implicit T: IsRealTensorK[T, R]): F[T[(B, A)], T[A], T[B]] =
    new F[T[(B, A)], T[A], T[B]] {
      import T._
      def name = "MVMul"
      def tag(tx1: Type[T[(B, A)]], tx2: Type[T[A]]) = T.ground[B]
      def forward(x1: T[(B, A)], x2: T[A]) = mvMul(x1, x2)
      def backward1(dy: T[B], y: T[B], x1: T[(B, A)], x2: T[A]) = vvMul(dy, x2)
      def backward2(dy: T[B], y: T[B], x1: T[(B, A)], x2: T[A]) = mvMul(transpose(x1), dy)
    }

}

/**
 * General tensor multiplication (contraction) that marginalizes out all axes between two tensors that match.
 * Einstein summation
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Contract extends PolyOp2 {
  implicit def contractF[T[_], R, A, B, C](implicit T: IsRealTensorK[T, R], sd: SymDiff.Aux[A, B, C]) =
    new F[T[A], T[B], T[C]] {
      def name = "Contract"
      def tag(tx1: Type[T[A]], tx2: Type[T[B]]) = T.ground[C]
      def forward(x1: T[A], x2: T[B]) = T.contract(x1, x2)(sd)
      def backward1(dy: T[C], y: T[C], x1: T[A], x2: T[B]) = T.contract(dy, x2)(sd.recoverLeft)
      def backward2(dy: T[C], y: T[C], x1: T[A], x2: T[B]) = T.contract(dy, x1)(sd.recoverRight)
    }

}
