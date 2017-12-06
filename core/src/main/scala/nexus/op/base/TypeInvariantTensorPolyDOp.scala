package nexus.op.base

import nexus.algebra._
import nexus.{$$, DOp1, DOp2, PolyDOp1, PolyDOp2}

import scala.annotation._

/**
 * Base class for unary polymorphic functions with the following groundings:
 *   - (implicit Ev[T, R]) => (T[As] => T[As])
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class TypeInvariantTensorPolyDOp1[Ev[T[_ <: $$], R] <: GradH[T]] extends PolyDOp1 { self =>

  def name: String

  def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: Ev[T, R]): T[A]

  def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: Ev[T, R]): T[A]

  @implicitNotFound("Cannot apply this operator to ${X}.")
  trait F[X, Y] extends DOp1[X, Y]

  object F {
    /**
     * Synthesizes an operator for tensor type [[T]] and axes [[A]].
     */
    implicit def synthesize[T[_ <: $$], R, A <: $$](implicit T: Ev[T, R]): F[T[A], T[A]] = new OpImpl[T, R, A]
  }

  class OpImpl[T[_ <: $$], R, A <: $$](implicit T: Ev[T, R]) extends F[T[A], T[A]] {
    def name = self.name
    def tag = T.ground[A]
    def forward(x: T[A]) = self.forward(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = self.backward(dy, y, x)
  }

}

/**
 * Base class for binary polymorphic functions with the following groundings:
 *   - (implicit Ev[T, R]) => ((T[As], T[As]) => T[As])
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class TypeInvariantTensorPolyDOp2[Ev[T[_ <: $$], R] <: GradH[T]] extends PolyDOp2 { self =>

  def name: String
  def forward[T[_ <: $$], R, A <: $$](x1: T[A], x2: T[A])(implicit T: Ev[T, R]): T[A]
  def backward1[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: Ev[T, R]): T[A]
  def backward2[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: Ev[T, R]): T[A]

  @implicitNotFound("Cannot apply this operator to ${X1} and ${X2}.")
  trait F[X1, X2, Y] extends DOp2[X1, X2, Y]

  object F {
    implicit def synthesize[T[_ <: $$], E, A <: $$](implicit T: Ev[T, E]): F[T[A], T[A], T[A]] =
      new OpImpl[T, E, A]
  }

  class OpImpl[T[_ <: $$], E, A <: $$](implicit T: Ev[T, E]) extends F[T[A], T[A], T[A]] {
    def name = self.name
    def tag = T.ground[A]
    def forward(x1: T[A], x2: T[A]) = self.forward(x1, x2)
    def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = self.backward1(dy, y, x1, x2)
    def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = self.backward2(dy, y, x1, x2)
  }
}

