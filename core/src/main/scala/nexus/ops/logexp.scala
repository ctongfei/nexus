package nexus.ops

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * Exponentiation of a real number.
 * - Input: A real number \(x\).
 * - Output: A real number \(y\) computed as \(y = e^x\).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Exp extends PolyOp1 {

  implicit def expF[R](implicit R: IsReal[R]): F[R, R] = new F[R, R] {
    def name = "Exp"
    def tag = R
    def forward(x: R) = R.exp(x)
    def backward(dy: R, y: R, x: R) = dy * y
  }

  /**
   * Element-wise exponentiation.
   *
   * Input: Any tensor 「bb"x"」 with axes 「i_1 , ... , i_d」.
   *
   * Output: A tensor 「bb"y"」 with the same shape as 「bb"x"」, computed as
   * 「y_(i_1, ..., i_d) = exp(x_(i_1, ..., i_d))」.
   *
   * @author Tongfei Chen
   * @since 0.1.0
   */
  object Elementwise extends PolyOp1 {

    implicit def expElementwiseF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] = new F[T[A], T[A]] {
      def name = "Exp.Elementwise"
      def tag = T.ground[A]
      def forward(x: T[A]) = T.eExp(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| y
    }

  }
}

/**
 * Element-wise natural logarithm.
 * - Input: A real number \(x\).
 *
 */
object Log extends PolyOp1 {

  implicit def logF[R](implicit R: IsReal[R]): F[R, R] = new F[R, R] {
    def name = "Log"
    def tag = R
    def forward(x: R) = R.log(x)
    def backward(dy: R, y: R, x: R) = dy / x
  }

  object Elementwise extends PolyOp1 {

    implicit def logElementwiseF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] = new F[T[A], T[A]] {
      def name = "Log.Elementwise"
      def tag = T.ground[A]
      def forward(x: T[A]) = T.eLog(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |/| x
    }

  }
}

object Log1p extends PolyOp1 {

  implicit def log1pF[R](implicit R: IsReal[R]): F[R, R] = new F[R, R] {
    import R._
    def name = "Log1p"
    def tag = R
    def forward(x: R) = log1p(x)
    def backward(dy: R, y: R, x: R) = dy * inv(x + one)
  }

  object Elementwise extends PolyOp1 {

    implicit def log1pElementwiseF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] = new F[T[A], T[A]] {
      import T._
      def name = "Log1p.Elementwise"
      def tag = T.ground[A]
      def forward(x: T[A]) = eLog1p(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| eInv(addS(x, T.R.one))
    }

  }

}


object Expm1 extends PolyOp1 {

  implicit def expm1F[R](implicit R: IsReal[R]): F[R, R] = new F[R, R] {
    import R._
    def name = "Expm1"
    def tag = R
    def forward(x: R) = expm1(x)
    def backward(dy: R, y: R, x: R) = dy * y
  }


}

object LogSumExp extends PolyOp1 {

  implicit def logSumExpF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], R] = new F[T[A], R] {
    def name = "LogSumExp"
    def tag = T.R
    def forward(x: T[A]) = ???
    def backward(dy: R, y: R, x: T[A]) = ???
  }

}

