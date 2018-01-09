package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import scala.annotation._

/**
 * Wraps a scalar to a 0-dim tensor.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ScalarToTensor0 extends PolyOp1 {
  implicit def scalar[T[_], R](implicit T: IsRealTensorH[T, R]): F[R, T[Unit]] =
    new F[R, T[Unit]] {
      def name = "ScalarToTensor0"
      def tag(tx: Type[R]) = T.ground[Unit]
      def differentiable = true
      def forward(x: R) = T.wrapScalar(x)
      def backward(dy: T[Unit], y: T[Unit], x: R) = T.unwrapScalar(dy)
    }
}

/**
 * Unwraps a 0-dim tensor to a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Tensor0ToScalar extends PolyOp1 {

  implicit def scalar[T[_], R](implicit T: IsRealTensorH[T, R]): F[T[Unit], R] =
    new F[T[Unit], R] {
      def name = "Tensor0ToTensor"
      def tag(tx: Type[T[Unit]]) = T.R
      def differentiable = true
      def forward(x: T[Unit]) = T.unwrapScalar(x)
      def backward(dy: R, y: R, x: T[Unit]) = T.wrapScalar(dy)
  }

}


/**
 * Transforms each
 * @author Tongfei Chen
 */
object OneHot extends ParamPolyOp1

/**
 * Slices a tensor along a specific axis.
 * @example {{{ SliceAlong(Width -> 3) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
object SliceAlong extends ParamPolyOp1
