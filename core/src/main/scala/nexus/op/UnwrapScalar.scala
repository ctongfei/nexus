package nexus.op

import nexus._
import nexus.algebra._

/**
 * Unwraps a 0-dim tensor to a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object UnwrapScalar extends PolyDOp1[UnwrapScalarF]

@implicitNotFound("Cannot apply UnwrapScalar to ${X}.")
trait UnwrapScalarF[X, Y] extends DOp1[X, Y] {
  def name = "UnwrapScalar"
}

object UnwrapScalarF {

  implicit def scalar[T[_ <: $$], R](implicit T: TypedRealTensorOps[T, R]): UnwrapScalarF[T[$], R] =
    new UnwrapScalarF[T[$], R] {
      def gradOps = T.R
      def backward(dy: R, y: R, x: T[$]) = T.wrapScalar(dy)
      def forward(x: T[$]) = T.unwrapScalar(x)
    }

}
