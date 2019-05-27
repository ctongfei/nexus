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

  /**
   * @group Creation
   */
  def newGaussianTensor[U](μ: Double, σ2: Double, shape: Array[Int]): T[U]

  /**
   * Multiplicative inverse (reciprocal).
   * @groupname elementwise Elementwise math operations
   * @groupdesc arithmetic These operators operates elementwise on tensors
   * @group elementwise
   */
  def inv[U](x: T[U]): T[U]

  /**
   * Square root.
   * @group elementwise
   */
  def sqrt[U](x: T[U]): T[U]

  def log[U](x: T[U]): T[U]
  def exp[U](x: T[U]): T[U]
  def log1p[U](x: T[U]): T[U]
  def expm1[U](x: T[U]): T[U]

  def sin[U](x: T[U]): T[U]
  def cos[U](x: T[U]): T[U]
  def tan[U](x: T[U]): T[U]
  def arcsin[U](x: T[U]): T[U]
  def arccos[U](x: T[U]): T[U]
  def arctan[U](x: T[U]): T[U]


  def logistic[U](x: T[U]): T[U]

  def relu[U](x: T[U]): T[U]

  def abs[U](x: T[U]): T[U]
  def sgn[U](x: T[U]): T[U]

  def pos[U](x: T[U]): T[U]

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

  def ground[U]: IsRealTensor[T[U], R] = grounded.asInstanceOf[IsRealTensor[T[U], R]]
}

object IsRealTensorK {

  implicit def fromDType[T[_], R](implicit T: RealDType.Aux[R, T]): IsRealTensorK[T, R] = T.T

}


trait IsRealTensor[T, R] extends IsTensor[T, R] with Grad[T] {
  def elementType: IsReal[R]
}
