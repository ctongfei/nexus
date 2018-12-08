package nexus.diff.ops

import nexus.diff._
import nexus.tensor._
import nexus.tensor.syntax._
import nexus.tensor.typelevel._

/**
 * Scales a tensor by a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Scale extends PolyOp2 {

  implicit def scaleF[T[_], R, a](implicit T: IsRealTensorK[T, R]) = new F[R, T[a], T[a]] {
    def name = "Scale"
    def tag = Tag.realTensor[T, R, a]
    def forward(x1: R, x2: T[a]) = x2 :* x1
    def backward1(dy: T[a], y: T[a], x1: R, x2: T[a]) = dy ⋅ x2
    def backward2(dy: T[a], y: T[a], x1: R, x2: T[a]) = dy :* x1
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
    def tag = Tag.real[R]
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

  implicit def matMulF[T[_], R, a, b, c]
  (implicit T: IsRealTensorK[T, R]): F[T[(a, b)], T[(b, c)], T[(a, c)]] =
    new F[T[(a, b)], T[(b, c)], T[(a, c)]] {
      import T._
      def name = "MatMul"
      def tag = Tag.realTensor[T, R, (a, c)]
      def forward(x1: T[(a, b)], x2: T[(b, c)]) = matMul(x1, x2)
      def backward1(dy: T[(a, c)], y: T[(a, c)], x1: T[(a, b)], x2: T[(b, c)]) = matMul(dy, transpose(x2))
      def backward2(dy: T[(a, c)], y: T[(a, c)], x1: T[(a, b)], x2: T[(b, c)]) = matMul(transpose(x1), dy)
    }
}

/**
 * Transposition of a matrix (2-D tensor).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Transpose extends PolyOp1 {
  implicit def transposeF[T[_], R, a <: Dim, b <: Dim](implicit T: IsRealTensorK[T, R]) = new F[T[(a, b)], T[(b, a)]] {
    def name = "Transpose"
    def tag = Tag.realTensor[T, R, (b, a)]
    def forward(x: T[(a, b)]) = T.transpose(x)
    def backward(dy: T[(b, a)], y: T[(b, a)], x: T[(a, b)]) = T.transpose(dy)
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

  implicit def mvMulF[T[_], R, a <: Dim, b <: Dim](implicit T: IsRealTensorK[T, R]): F[T[(b, a)], T[a], T[b]] =
    new F[T[(b, a)], T[a], T[b]] {
      import T._
      def name = "MVMul"
      def tag = Tag.realTensor[T, R, b]
      def forward(x1: T[(b, a)], x2: T[a]) = mvMul(x1, x2)
      def backward1(dy: T[b], y: T[b], x1: T[(b, a)], x2: T[a]) = vvMul(dy, x2)
      def backward2(dy: T[b], y: T[b], x1: T[(b, a)], x2: T[a]) = mvMul(transpose(x1), dy)
    }

}

/**
 * General tensor multiplication (contraction) that marginalizes out all axes between two tensors that match.
 * Einstein summation
 * References:
 *  - T Chen (2017): Typesafe abstractions for tensor operations. SCALA.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Contract extends PolyOp2 {
  implicit def contractF[T[_], R, a, b, c](implicit T: IsRealTensorK[T, R], sd: SymDiff.Aux[a, b, c]) =
    new F[T[a], T[b], T[c]] {
      def name = "Contract"
      def tag = Tag.realTensor[T, R, c]
      def forward(x1: T[a], x2: T[b]) = T.contract(x1, x2)(sd)
      def backward1(dy: T[c], y: T[c], x1: T[a], x2: T[b]) = T.contract(dy, x2)(sd.recoverLeft)
      def backward2(dy: T[c], y: T[c], x1: T[a], x2: T[b]) = T.contract(dy, x1)(sd.recoverRight)
    }

}
