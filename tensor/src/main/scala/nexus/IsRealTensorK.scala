package nexus

import nexus.typelevel._
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

  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]): T[A]


  def inv[A](x: T[A]): T[A]

  def sqrt[A](x: T[A]): T[A]

  def log[A](x: T[A]): T[A]
  def exp[A](x: T[A]): T[A]
  def log1p[A](x: T[A]): T[A]
  def expm1[A](x: T[A]): T[A]

  def sin[A](x: T[A]): T[A]
  def cos[A](x: T[A]): T[A]
  def tan[A](x: T[A]): T[A]
  def arcsin[A](x: T[A]): T[A]
  def arccos[A](x: T[A]): T[A]
  def arctan[A](x: T[A]): T[A]


  def sigmoid[A](x: T[A]): T[A]

  def relu[A](x: T[A]): T[A]

  def abs[A](x: T[A]): T[A]
  def sgn[A](x: T[A]): T[A]

  def pos[A](x: T[A]): T[A]

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
