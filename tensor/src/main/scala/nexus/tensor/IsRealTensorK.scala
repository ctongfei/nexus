package nexus.tensor

import scala.annotation._

/**
 * Typeclass that describes the algebraic structures on tensors whose elements are real numbers.
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that ${T} is a typed tensor of ${R}.")
trait IsRealTensorK[T[_], R] extends IsTensorK[T, R] with GradK[T] { self =>

  type ElementTag[r] = IsReal[r]

  implicit val R: IsReal[R]
  def elementType = R

  def newTensor[A](shape: Array[Int]): T[A]

  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]): T[A]

  def zeroBy[A](x: T[A]): T[A]

  def add[A](x1: T[A], x2: T[A]): T[A]

  def addI[A](x1: T[A], x2: T[A]): T[A]

  def addS[A](x: T[A], u: R): T[A]

  def neg[A](x: T[A]): T[A]

  def sub[A](x1: T[A], x2: T[A]): T[A]

  def subS[A](x: T[A], u: R): T[A]

  def eMul[A](x1: T[A], x2: T[A]): T[A]

  def eDiv[A](x1: T[A], x2: T[A]): T[A]

  def scale[A](x: T[A], u: R): T[A]

  def eInv[A](x: T[A]): T[A]

  def eSqr[A](x: T[A]): T[A]

  def eSqrt[A](x: T[A]): T[A]

  def transpose[A, B](x: T[(A, B)]): T[(B, A)]

  def eLog[A](x: T[A]): T[A]
  def eExp[A](x: T[A]): T[A]
  def eLog1p[A](x: T[A]): T[A]
  def eExpm1[A](x: T[A]): T[A]

  def eSin[A](x: T[A]): T[A]
  def eCos[A](x: T[A]): T[A]
  def eTan[A](x: T[A]): T[A]

  def sigmoid[A](x: T[A]): T[A]

  def relu[A](x: T[A]): T[A]

  def eAbs[A](x: T[A]): T[A]
  def eSgn[A](x: T[A]): T[A]

  def pos[A](x: T[A]): T[A]

  def sum(x: T[_]): R

  def mmMul[A, B, C](x: T[(A, B)], y: T[(B, C)]): T[(A, C)]

  def mvMul[A, B](x: T[(A, B)], y: T[B]): T[A]

  def vvMul[A, B](x: T[A], y: T[B]): T[(A, B)]

  def dot[A](x: T[A], y: T[A]): R

  def contract[A, B, C](x: T[A], y: T[B])(implicit sd: SymDiff.Aux[A, B, C]): T[C]

  def ground[A]: IsRealTensor[T[A], R] =
    new IsRealTensor[T[A], R] {
      def elementType = self.elementType
      def mutable = true
      def zeroBy(x: T[A]) = self.zeroBy(x)
      def add(x1: T[A], x2: T[A]) = self.add(x1, x2)
      def addS(x1: T[A], x2: Double) = self.addS(x1, R.fromDouble(x2))
      def addI(x1: T[A], x2: T[A]): Unit = self.addI(x1, x2)
      def sub(x1: T[A], x2: T[A]) = self.sub(x1, x2)
      def neg(x: T[A]) = self.neg(x)
      def eMul(x1: T[A], x2: T[A]) = self.eMul(x1, x2)
      def eDiv(x1: T[A], x2: T[A]) = self.eDiv(x1, x2)
      def scale(x: T[A], k: Double) = self.scale(x, R.fromDouble(k))
      def eSqrt(x: T[A]) = self.eSqrt(x)
    }

}


trait IsRealTensor[T, R] extends IsTensor[T, R] with Grad[T] {
  def elementType: IsReal[R]
}
