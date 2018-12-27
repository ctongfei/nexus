package nexus

import shapeless._

import scala.annotation._

/**
 * Proves that an expression is differentiable with respect to an expressive of a specific type.
 *
 * An instance of this typeclass should be attached to differentiable expressions ([[nexus.diff.Symbolic]])
 * or differentiable operators ([[nexus.diff.Op1]] etc.).
 *
 * This typeclass contains basic math operations on gradients that are used by optimizers
 * ([[nexus.diff.optimizers.FirstOrderOptimizer]]).
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Type ${X} is not differentiable with respect to.")
trait Grad[@specialized(Float, Double) X] {

  def mutable: Boolean

  def zeroBy(x: X): X

  def add(x1: X, x2: X): X

  def addScalar(x1: X, x2: Double): X

  def addInplace(x1: X, x2: X): X

  def sub(x1: X, x2: X): X

  def neg(x: X): X

  def mul(x1: X, x2: X): X

  def div(x1: X, x2: X): X

  def scale(x: X, k: Double): X

  def sqrt(x: X): X

}

object Grad extends ProductTypeClassCompanion[Grad] {

  /** Summons the implicit `Grad` instance of a type. */
  @inline def apply[X](implicit X: Grad[X]) = X

  /** Grounds from a higher-kinded `GradK`. */
  implicit def ground[T[_], A](implicit T: GradK[T]): Grad[T[A]] = T.ground[A]

  //implicit def groundFromDType[T[_], R, A](implicit R: RealDType.Aux[R, T]): Grad[T[A]] = R.T.ground[A]

  /**
   * Automatic typeclass derivation on product types using `Shapeless`.
   */
  object typeClass extends ProductTypeClass[Grad] {

    class Product[H, T <: HList](H: Grad[H], T: Grad[T]) extends Grad[H :: T] {
      def mutable = H.mutable && T.mutable
      def zeroBy(x: H :: T) = H.zeroBy(x.head) :: T.zeroBy(x.tail)
      def add(x1: H :: T, x2: H :: T) = H.add(x1.head, x2.head) :: T.add(x1.tail, x2.tail)
      def addScalar(x1: H :: T, x2: Double) = H.addScalar(x1.head, x2) :: T.addScalar(x1.tail, x2)
      def addInplace(x1: H :: T, x2: H :: T) = H.addInplace(x1.head, x2.head) :: T.addInplace(x1.tail, x2.tail)

      def sub(x1: H :: T, x2: H :: T) = H.sub(x1.head, x2.head) :: T.sub(x1.tail, x2.tail)
      def neg(x: H :: T) = H.neg(x.head) :: T.neg(x.tail)
      def mul(x1: H :: T, x2: H :: T) = H.mul(x1.head, x2.head) :: T.mul(x1.tail, x2.tail)
      def div(x1: H :: T, x2: H :: T) = H.div(x1.head, x2.head) :: T.div(x1.tail, x2.tail)
      def scale(x: H :: T, k: Double) = H.scale(x.head, k) :: T.scale(x.tail, k)
      def sqrt(x: H :: T) = H.sqrt(x.head) :: T.sqrt(x.tail)
    }

    def product[H, T <: HList](H: Grad[H], T: Grad[T]) = new Product(H, T)

    object emptyProduct extends Grad[HNil] {
      def add(x1: HNil, x2: HNil) = HNil
      def sub(x1: HNil, x2: HNil) = HNil
      def div(x1: HNil, x2: HNil) = HNil
      def addInplace(x1: HNil, x2: HNil) = HNil
      def scale(x: HNil, k: Double) = HNil
      def zeroBy(x: HNil) = HNil
      def mul(x1: HNil, x2: HNil) = HNil
      def neg(x: HNil) = HNil
      def sqrt(x: HNil) = HNil
      def mutable = true
      def addScalar(x1: HNil, x2: Double) = HNil
    }

    def project[F, G](G: => Grad[G], fg: F => G, gf: G => F): Grad[F] = new Grad[F] {
      def mutable = G.mutable
      def zeroBy(x: F) = gf(G.zeroBy(fg(x)))
      def add(x1: F, x2: F) = gf(G.add(fg(x1), fg(x2)))
      def addScalar(x1: F, x2: Double) = gf(G.addScalar(fg(x1), x2))
      def addInplace(x1: F, x2: F) = gf(G.addInplace(fg(x1), fg(x2)))
      def sub(x1: F, x2: F) = gf(G.sub(fg(x1), fg(x2)))
      def neg(x: F) = gf(G.neg(fg(x)))
      def mul(x1: F, x2: F) = gf(G.mul(fg(x1), fg(x2)))
      def div(x1: F, x2: F) = gf(G.div(fg(x1), fg(x2)))
      def scale(x: F, k: Double) = gf(G.scale(fg(x), k))
      def sqrt(x: F) = gf(G.sqrt(fg(x)))
    }
  }
}

trait GradK[T[_]] {
  def ground[A]: Grad[T[A]]
}
