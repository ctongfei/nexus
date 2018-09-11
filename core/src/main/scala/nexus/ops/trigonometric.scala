package nexus.ops

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * Sine on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sin extends PolyOp1 {

  implicit def sinF[R](implicit R: IsReal[R]): F[R, R] = new F[R, R] {
    def name = "Sin"
    def tag = R
    def forward(x: R) = R.sin(x)
    def backward(dy: R, y: R, x: R) = dy * R.cos(x)
  }

  /**
   * Elementwise sine on a tensor.
   */
  object Elementwise extends PolyOp1 {

    implicit def tensor[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] = new F[T[A], T[A]] {
      def name = "Sin.Elementwise"
      def tag = T.ground[A]
      def forward(x: T[A]) = T.eSin(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| T.eCos(x)
    }

  }
}


/**
 * Cosine on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Cos extends PolyOp1 {

  implicit def cosF[R](implicit R: IsReal[R]): F[R, R] = new F[R, R] {
    def name = "Cos"
    def tag = R
    def forward(x: R) = R.cos(x)
    def backward(dy: R, y: R, x: R) = -dy * R.sin(x)
  }

  /**
   * Elementwise cosine on a tensor.
   */
  object Elementwise extends PolyOp1 {

    implicit def tensor[T[_], R, A](implicit T: IsRealTensorK[T, R]) = new F[T[A], T[A]] {
      def name = "Cos.Elementwise"
      def tag = T.ground[A]
      def forward(x: T[A]) = T.eCos(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = -dy |*| T.eSin(x)
    }

  }
}

object Tan extends PolyOp1

object ArcSin extends PolyOp1

object ArcCos extends PolyOp1

object ArcTan extends PolyOp1

object ATan2 extends PolyOp2

