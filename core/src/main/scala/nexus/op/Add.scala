package nexus.op

import nexus._
import nexus.algebra._

/**
 * Addition of two object if these two objects are of the
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Add extends PolyDOp2[AddF]

@implicitNotFound("Cannot apply Add to ${X1} and ${X2}.")
trait AddF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Add"
}

object AddF {

  implicit def scalar[R](implicit ops: RealOps[R]): AddF[R, R, R] =
    new AddF[R, R, R] {
      import ops._
      def gradOps = ops
      def forward(x1: R, x2: R) = add(x1, x2)
      def backward1(dy: R, y: R, x1: R, x2: R) = dy
      def backward2(dy: R, y: R, x1: R, x2: R) = dy
    }

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: TypedRealTensorOps[T, R]): AddF[T[A], T[A], T[A]] =
    new AddF[T[A], T[A], T[A]] {
      def gradOps = T.ground[A]
      def forward(x1: T[A], x2: T[A]) = x1 + x2
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy
    }

}
