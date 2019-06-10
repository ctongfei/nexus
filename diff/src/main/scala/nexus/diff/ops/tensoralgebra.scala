package nexus.diff.ops

import nexus.diff._
import nexus._
import nexus.syntax._
import nexus.typelevel._

/**
 * Scales a tensor by a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Scale extends PolyOp2 {

  implicit def scaleF[T[_], R, I](implicit T: IsRealTensorK[T, R]) = new P[T[I], R, T[I]] {
    def name = "Scale"
    def tag = Tag.realTensor[T, R, I]
    def forward(x1: T[I], x2: R) = x1 :* x2
    def backward1(dy: T[I], y: T[I], x1: T[I], x2: R) = dy :* x2
    def backward2(dy: T[I], y: T[I], x1: T[I], x2: R) = dy ⋅ x1
  }

  val By = Curried2

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
  implicit def dotF[T[_], R, I](implicit T: IsRealTensorK[T, R]) = new P[T[I], T[I], R] {
    def name = "Dot"
    def tag = Tag.real[R]
    def forward(x1: T[I], x2: T[I]) = T.dot(x1, x2)
    def backward1(dy: R, y: R, x1: T[I], x2: T[I]) = x2 :* dy
    def backward2(dy: R, y: R, x1: T[I], x2: T[I]) = x1 :* dy
  }
}

/**
 * Matrix multiplication of two matrices (2-D tensors).
 * @note The second axis of the first operand and the first axis of the second operand must match.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object MatMul extends PolyOp2 {

  implicit def matMulF[T[_], R, I <: Dim, J <: Dim, K <: Dim]
  (implicit T: IsRealTensorK[T, R]): P[T[(I, J)], T[(J, K)], T[(I, K)]] =
    new P[T[(I, J)], T[(J, K)], T[(I, K)]] {
      import T._
      def name = "MatMul"
      def tag = Tag.realTensor[T, R, (I, K)]
      def forward(x1: T[(I, J)], x2: T[(J, K)]) = matMul(x1, x2)
      def backward1(dy: T[(I, K)], y: T[(I, K)], x1: T[(I, J)], x2: T[(J, K)]) = matMul(dy, transpose(x2))
      def backward2(dy: T[(I, K)], y: T[(I, K)], x1: T[(I, J)], x2: T[(J, K)]) = matMul(transpose(x1), dy)
    }
}

/**
 * Transposition of a matrix (2-D tensor).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Transpose extends PolyOp1 {
  implicit def transposeF[T[_], R, I <: Dim, J <: Dim](implicit T: IsRealTensorK[T, R]): P[T[(I, J)], T[(J, I)]] =
    new P[T[(I, J)], T[(J, I)]] {
      def name = "Transpose"
      def tag = Tag.realTensor[T, R, (J, I)]
      def forward(x: T[(I, J)]) = T.transpose(x)
      def backward(dy: T[(J, I)], y: T[(J, I)], x: T[(I, J)]) = T.transpose(dy)
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

  implicit def mvMulF[T[_], R, I <: Dim, J <: Dim](implicit T: IsRealTensorK[T, R]): P[T[(J, I)], T[I], T[J]] =
    new P[T[(J, I)], T[I], T[J]] {
      import T._
      def name = "MVMul"
      def tag = Tag.realTensor[T, R, J]
      def forward(x1: T[(J, I)], x2: T[I]) = mvMul(x1, x2)
      def backward1(dy: T[J], y: T[J], x1: T[(J, I)], x2: T[I]) = vvMul(dy, x2)
      def backward2(dy: T[J], y: T[J], x1: T[(J, I)], x2: T[I]) = mvMul(transpose(x1), dy)
    }

}

object VVMul extends PolyOp2 {

  implicit def vvMulF[T[_], R, I <: Dim, J <: Dim](implicit T: IsRealTensorK[T, R]): P[T[I], T[J], T[(I, J)]] =
    new P[T[I], T[J], T[(I, J)]] {
      import T._
      def name = "VVMul"
      def tag = ???
      def forward(x1: T[I], x2: T[J]) = ???
      def backward1(dy: T[(I, J)], y: T[(I, J)], x1: T[I], x2: T[J]) = ???
      def backward2(dy: T[(I, J)], y: T[(I, J)], x1: T[I], x2: T[J]) = ???
    }

}

object OuterProduct extends PolyOp2 {



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
  implicit def contractF[T[_], R, U, V, W](implicit T: IsRealTensorK[T, R], sd: SymDiff.Aux[U, V, W]): P[T[U], T[V], T[W]] =
    new P[T[U], T[V], T[W]] {
      def name = "Contract"
      def tag = Tag.realTensor[T, R, W]
      def forward(x1: T[U], x2: T[V]) = T.contract(x1, x2)(sd)
      def backward1(dy: T[W], y: T[W], x1: T[U], x2: T[V]) = T.contract(dy, x2)(sd.recoverLeft)
      def backward2(dy: T[W], y: T[W], x1: T[U], x2: T[V]) = T.contract(dy, x1)(sd.recoverRight)
    }

}
