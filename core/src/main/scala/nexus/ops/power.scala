package nexus.ops

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * Square of a number.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sqr extends PolyOp1 {

  implicit def sqrF[R](implicit R: IsReal[R]): F[R, R] = new F[R, R] {
    def name = "Sqr"
    def tag(tx: Type[R]) = tx
    def forward(x: R) = R.sqr(x)
    def backward(dy: R, y: R, x: R) = dy * x * 2
  }

  /**
   * Elementwise square.
   */
  object Elementwise extends PolyOp1 {

    implicit def sqrElementwiseF[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] = new F[T[A], T[A]] {
      def name = "Sqr.Elementwise"
      def tag(tx: Type[T[A]]) = tx
      def forward(x: T[A]) = T.eSqr(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| x :* 2
    }

  }
}


object Sqrt extends PolyOp1 {

  implicit def sqrtF[R](implicit R: IsReal[R]): F[R, R] = new F[R, R] {
    def name = "Sqrt"
    def tag(tx: Type[R]) = tx
    def forward(x: R) = R.sqrt(x)
    def backward(dy: R, y: R, x: R) = dy * R.inv(y) * 0.5
  }

  object Elementwise extends PolyOp1 {

    implicit def sqrtElementwiseF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] = new F[T[A], T[A]] {
      def name = "Sqrt.Elementwise"
      def tag(tx: Type[T[A]]) = tx
      def forward(x: T[A]) = T.eSqrt(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| T.eInv(y) :* 0.5
    }

  }
}

object Power extends PolyOp2 {


}

