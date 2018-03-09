package nexus.algebra

import shapeless._
import scala.annotation._

/**
 * Typeclass witnessing that the specific type which an expression is differentiable with respect to.
 *
 * An instance of this typeclass should be attached to differentiable expressions ([[nexus.Expr]])
 * or differentiable operators ([[nexus.Op1]] etc.).
 *
 * This typeclass contains basic math operations on gradients that are used by optimizers
 * ([[nexus.optimizer.FirstOrderOptimizer]]).
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Type ${X} is not differentiable with respect to.")
trait Grad[@specialized(Float, Double) X] extends Type[X] {

  def mutable: Boolean

  def zeroBy(x: X): X

  def add(x1: X, x2: X): X

  def addS(x1: X, x2: Double): X

  def addI(x1: X, x2: X): Unit

  def sub(x1: X, x2: X): X

  def neg(x: X): X

  def eMul(x1: X, x2: X): X

  def eDiv(x1: X, x2: X): X

  def scale(x: X, k: Double): X

  def eSqrt(x: X): X

}

object Grad extends ProductTypeClassCompanion[Grad] {

  /** Summons the implicit `Grad` instance of a type. */
  @inline def apply[X](implicit X: Grad[X]) = X

  /** Grounds from a higher-kinded `GradH`. */
  implicit def ground[T[_], A](implicit T: GradK[T]): Grad[T[A]] = T.ground[A]

  /**
   * Automatic typeclass derivation on product types using `Shapeless`.
   */
  object typeClass extends ProductTypeClass[Grad] {

    class Product[H, T <: $$](H: Grad[H], T: Grad[T]) extends Grad[H :: T] {
      def mutable = H.mutable && T.mutable
      def zeroBy(x: H :: T) = H.zeroBy(x.head) :: T.zeroBy(x.tail)
      def add(x1: H :: T, x2: H :: T) = H.add(x1.head, x2.head) :: T.add(x1.tail, x2.tail)
      def addS(x1: H :: T, x2: Double) = H.addS(x1.head, x2) :: T.addS(x1.tail, x2)
      def addI(x1: H :: T, x2: H :: T) = {
        H.addI(x1.head, x2.head)
        T.addI(x1.tail, x2.tail)
      }
      def sub(x1: H :: T, x2: H :: T) = H.sub(x1.head, x2.head) :: T.sub(x1.tail, x2.tail)
      def neg(x: H :: T) = H.neg(x.head) :: T.neg(x.tail)
      def eMul(x1: H :: T, x2: H :: T) = H.eMul(x1.head, x2.head) :: T.eMul(x1.tail, x2.tail)
      def eDiv(x1: H :: T, x2: H :: T) = H.eDiv(x1.head, x2.head) :: T.eDiv(x1.tail, x2.tail)
      def scale(x: H :: T, k: Double) = H.scale(x.head, k) :: T.scale(x.tail, k)
      def eSqrt(x: H :: T) = H.eSqrt(x.head) :: T.eSqrt(x.tail)
    }

    def product[H, T <: $$](H: Grad[H], T: Grad[T]) = new Product(H, T)

    object emptyProduct extends Grad[$] {
      def add(x1: $, x2: $) = $
      def sub(x1: $, x2: $) = $
      def eDiv(x1: $, x2: $) = $
      def addI(x1: $, x2: $) = ()
      def scale(x: $, k: Double) = $
      def zeroBy(x: $) = $
      def eMul(x1: $, x2: $) = $
      def neg(x: $) = $
      def eSqrt(x: $) = $
      def mutable = true
      def addS(x1: $, x2: Double) = $
    }

    def project[F, G](G: => Grad[G], fg: F => G, gf: G => F): Grad[F] = new Grad[F] {
      def mutable = G.mutable
      def zeroBy(x: F) = gf(G.zeroBy(fg(x)))
      def add(x1: F, x2: F) = gf(G.add(fg(x1), fg(x2)))
      def addS(x1: F, x2: Double) = gf(G.addS(fg(x1), x2))
      def addI(x1: F, x2: F) = G.addI(fg(x1), fg(x2))
      def sub(x1: F, x2: F) = gf(G.sub(fg(x1), fg(x2)))
      def neg(x: F) = gf(G.neg(fg(x)))
      def eMul(x1: F, x2: F) = gf(G.eMul(fg(x1), fg(x2)))
      def eDiv(x1: F, x2: F) = gf(G.eDiv(fg(x1), fg(x2)))
      def scale(x: F, k: Double) = gf(G.scale(fg(x), k))
      def eSqrt(x: F) = gf(G.eSqrt(fg(x)))
    }
  }
}

trait GradK[T[_]] extends TypeK[T] {
  def ground[A]: Grad[T[A]]
}
