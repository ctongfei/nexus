package nexus.op

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
    def tag(tx: Type[R]) = tx
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
      def tag(tx: Type[T[A]]) = tx
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
    def tag(tx: Type[R]) = tx
    def forward(x: R) = R.log(x)
    def backward(dy: R, y: R, x: R) = dy / x
  }

  object Elementwise extends PolyOp1 {

    implicit def logElementwiseF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] = new F[T[A], T[A]] {
      def name = "Log.Elementwise"
      def tag(tx: Type[T[A]]) = tx
      def forward(x: T[A]) = T.eLog(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |/| x
    }

  }
}

object Log1p extends PolyOp1
object Expm1 extends PolyOp1

object LogSumExp extends PolyOp1

