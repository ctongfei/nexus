package nexus.algebra

import nexus._

/**
 * Typeclass witnessing that the specific type can be differentiated with respect to.
 *
 * A typeclass attached on differentiable expressions ([[nexus.DExpr]])
 * or differentiable operators ([[nexus.DOp1]] etc.) that contains basic math operations on gradients.
 * These are used by optimizers ([[nexus.optimizer.FirstOrderOptimizer]]).
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
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

object Grad {

  implicit def ground[T[_ <: $$], A <: $$](implicit T: GradH[T]): Grad[T[A]] = T.ground[A]

}


//TODO: automatic typeclass derivation for all sum/product types?

trait GradH[T[_ <: $$]] extends TypeH[T] {

  def ground[A <: $$]: Grad[T[A]]

}
