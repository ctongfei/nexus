package nexus.op

import nexus._
import nexus.algebra._

/**
 * Wraps a scalar to a 0-dim tensor.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object WrapScalar extends PolyDOp1[WrapScalarF]

trait WrapScalarF[X, Y] extends DOp1[X, Y] {
  def name = "WrapScalar"
}

object WrapScalarF {

  implicit def scalar[T[_ <: $$], R](implicit T: TypedRealTensorOps[T, R]): WrapScalarF[R, T[$]] =
    new WrapScalarF[R, T[$]] {
      def gradOps = T.ground[$]
      def backward(dy: T[$], y: T[$], x: R) = T.unwrapScalar(dy)
      def forward(x: R) = T.wrapScalar(x)
    }

}
