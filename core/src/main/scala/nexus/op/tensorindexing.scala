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
object ScalarToTensor0 extends PolyDOp1 {
  implicit def scalar[T[_ <: $$], R](implicit T: IsRealTensor[T, R]): F[R, T[$]] =
    new F[R, T[$]] {
      def name = "ScalarToTensor0"
      def tag = T.ground[$]
      def forward(x: R) = T.wrapScalar(x)
      def backward(dy: T[$], y: T[$], x: R) = T.unwrapScalar(dy)
    }
}

/**
 * Unwraps a 0-dim tensor to a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Tensor0ToScalar extends PolyDOp1 {

  implicit def scalar[T[_ <: $$], R](implicit T: IsRealTensor[T, R]): F[T[$], R] =
    new F[T[$], R] {
      def name = "Tensor0ToTensor"
      def tag = T.R
      def forward(x: T[$]) = T.unwrapScalar(x)
      def backward(dy: R, y: R, x: T[$]) = T.wrapScalar(dy)
  }

}


/**
 * Transforms each
 * @author Tongfei Chen
 */
case class OneHot[U](parameter: (U, Int)) extends ParaPolyOp1[(U, Int)]

/**
 * Slices a tensor along a specific axis.
 * @example {{{ SliceAlong(Width -> 3) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class SliceAlong[U](parameter: (U, Int)) extends ParaPolyDOp1[(U, Int)]
