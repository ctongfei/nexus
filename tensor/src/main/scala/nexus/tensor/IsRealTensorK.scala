package nexus.tensor

import nexus.tensor.typelevel._
import scala.annotation._

/**
 * Typeclass that describes the algebraic structures on tensors whose elements are real numbers.
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that ${T} is a typed tensor of ${R}.")
trait IsRealTensorK[T[_], @specialized(Float, Double) R] extends IsTensorK[T, R] with GradK[T] { self =>

  type ElementTag[r] = IsReal[r]
  override type TensorTag[te] = IsRealTensor[te, R]

  implicit val R: IsReal[R]
  def elementType = R

  def newTensor[a](shape: Array[Int]): T[a]

  def newGaussianTensor[a](μ: Double, σ2: Double, shape: Array[Int]): T[a]

  def zeroBy[a](x: T[a]): T[a]

  def add[a](x1: T[a], x2: T[a]): T[a]

  def addI[a](x1: T[a], x2: T[a]): T[a]

  def addS[a](x: T[a], u: R): T[a]

  def neg[a](x: T[a]): T[a]

  def sub[a](x1: T[a], x2: T[a]): T[a]

  def subS[a](x: T[a], u: R): T[a]

  def eMul[a](x1: T[a], x2: T[a]): T[a]

  def eDiv[a](x1: T[a], x2: T[a]): T[a]

  def scale[a](x: T[a], u: R): T[a]

  def eInv[a](x: T[a]): T[a]

  def eSqr[a](x: T[a]): T[a]

  def eSqrt[a](x: T[a]): T[a]

  def transpose[a, b](x: T[(a, b)]): T[(b, a)]

  def eLog[a](x: T[a]): T[a]
  def eExp[a](x: T[a]): T[a]
  def eLog1p[a](x: T[a]): T[a]
  def eExpm1[a](x: T[a]): T[a]

  def eSin[a](x: T[a]): T[a]
  def eCos[a](x: T[a]): T[a]
  def eTan[a](x: T[a]): T[a]

  def sigmoid[a](x: T[a]): T[a]

  def relu[a](x: T[a]): T[a]

  def eAbs[a](x: T[a]): T[a]
  def eSgn[a](x: T[a]): T[a]

  def pos[a](x: T[a]): T[a]

  def sum(x: T[_]): R

  def matMul[a, b, c](x: T[(a, b)], y: T[(b, c)]): T[(a, c)]

  def mvMul[a, b](x: T[(a, b)], y: T[b]): T[a]

  def vvMul[a, b](x: T[a], y: T[b]): T[(a, b)]

  def dot[a](x: T[a], y: T[a]): R

  def contract[a, b, c](x: T[a], y: T[b])(implicit sd: SymDiff.Aux[a, b, c]): T[c]

  def ground[a]: IsRealTensor[T[a], R] =
    new IsRealTensor[T[a], R] {
      def elementType = self.elementType
      def mutable = true
      def zeroBy(x: T[a]) = self.zeroBy(x)
      def add(x1: T[a], x2: T[a]) = self.add(x1, x2)
      def addS(x1: T[a], x2: Double) = self.addS(x1, R.fromDouble(x2))
      def addI(x1: T[a], x2: T[a]): Unit = self.addI(x1, x2)
      def sub(x1: T[a], x2: T[a]) = self.sub(x1, x2)
      def neg(x: T[a]) = self.neg(x)
      def eMul(x1: T[a], x2: T[a]) = self.eMul(x1, x2)
      def eDiv(x1: T[a], x2: T[a]) = self.eDiv(x1, x2)
      def scale(x: T[a], k: Double) = self.scale(x, R.fromDouble(k))
      def eSqrt(x: T[a]) = self.eSqrt(x)
    }

}


trait IsRealTensor[T, R] extends IsTensor[T, R] with Grad[T] {
  def elementType: IsReal[R]
}
