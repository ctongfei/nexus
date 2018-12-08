package nexus.tensor

import nexus.tensor.typelevel._
import scala.annotation._

/**
 * Typeclass that describes the algebraic structures on tensors whose elements are real numbers.
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that ${T} is a typed tensor of ${R}.")
trait IsRealTensorK[T[_], @specialized(Float, Double) R] extends RingTensorK[T, R] with GradK[T] { self =>

  type ElementTag[r] = IsReal[r]
  override type TensorTag[te] = IsRealTensor[te, R]

  implicit val R: IsReal[R]
  def elementType = R

  def newTensor[A](shape: Array[Int]): T[A]

  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]): T[A]


  def inv[A](x: T[A]): T[A]

  def sqrt[A](x: T[A]): T[A]

  def log[a](x: T[a]): T[a]
  def exp[a](x: T[a]): T[a]
  def log1p[a](x: T[a]): T[a]
  def expm1[a](x: T[a]): T[a]

  def sin[a](x: T[a]): T[a]
  def cos[a](x: T[a]): T[a]
  def tan[a](x: T[a]): T[a]

  def sigmoid[a](x: T[a]): T[a]

  def relu[a](x: T[a]): T[a]

  def abs[a](x: T[a]): T[a]
  def sgn[a](x: T[a]): T[a]

  def pos[a](x: T[a]): T[a]

  private object grounded extends IsRealTensor[T[Any], R] {
    def elementType = self.elementType
    def mutable = true
    def zeroBy(x: T[Any]) = self.zeroBy(x)
    def add(x1: T[Any], x2: T[Any]) = self.add(x1, x2)
    def addScalar(x1: T[Any], x2: Double) = self.addScalar(x1, R.fromDouble(x2))
    def addInplace(x1: T[Any], x2: T[Any]) = self.addInplace(x1, x2)
    def sub(x1: T[Any], x2: T[Any]) = self.sub(x1, x2)
    def neg(x: T[Any]) = self.neg(x)
    def mul(x1: T[Any], x2: T[Any]) = self.mul(x1, x2)
    def div(x1: T[Any], x2: T[Any]) = self.div(x1, x2)
    def scale(x: T[Any], k: Double) = self.scale(x, R.fromDouble(k))
    def sqrt(x: T[Any]) = self.sqrt(x)
  } // only allocate one object

  def ground[A]: IsRealTensor[T[A], R] = grounded.asInstanceOf[IsRealTensor[T[A], R]]
}

object IsRealTensorK {

  implicit def fromDType[T[_], R](implicit T: RealDType.Aux[R, T]): IsRealTensorK[T, R] = T.T

}


trait IsRealTensor[T, R] extends IsTensor[T, R] with Grad[T] {
  def elementType: IsReal[R]
}
