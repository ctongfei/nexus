package nexus.func.factory

import nexus._
import nexus.algebra._

import scala.annotation._

/**
 * Factory base class for unary polymorphic functions with the following groundings:
 *   - T[As] => T[As]
 * This class should only be inherited by objects.
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class AxesInvariantTensorOp1Factory[Ev[T[_ <: $$], E] <: GradH[T]] { self =>

  def name: String

  def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: Ev[T, E]): T[A]

  def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: Ev[T, E]): T[A]

  @implicitNotFound("Cannot apply this operator to ${X}.")
  trait Op[X, Y] extends DOp1[X, Y]

  object Op {
    /**
     * Synthesizes an operator for tensor type [[T]] and axes [[A]].
     */
    implicit def synthesize[T[_ <: $$], E, A <: $$](implicit T: Ev[T, E]): Op[T[A], T[A]] = new OpImpl[T, E, A]
  }

  class OpImpl[T[_ <: $$], E, A <: $$](implicit T: Ev[T, E]) extends Op[T[A], T[A]] {
    def name = self.name
    def tag = T.ground[A]
    def forward(x: T[A]) = self.forward(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = self.backward(dy, y, x)
  }

}
