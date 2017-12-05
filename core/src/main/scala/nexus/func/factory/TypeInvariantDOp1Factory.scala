package nexus.func.factory

import nexus._
import nexus.algebra._
import scala.annotation._

/**
 * Factory base class for unary polymorphic functions.
 * This class should only be inherited by objects.
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class TypeInvariantDOp1Factory[Ev[X] <: Grad[X]] { self =>

  def name: String

  def forward[X](x: X)(implicit X: Ev[X]): X

  def backward[X](dy: X, y: X, x: X)(implicit X: Ev[X]): X

  @implicitNotFound("Cannot apply this operator to ${X}.")
  trait Op[X, Y] extends DOp1[X, Y]

  object Op {
    /**
     * Synthesizes an operator for type [[X]].
     */
    implicit def synthesize[X](implicit X: Ev[X]): Op[X, X] = new OpImpl[X]
  }

  class OpImpl[X](implicit X: Ev[X]) extends Op[X, X] {
    def tag = X
    def name = self.name
    def forward(x: X) = self.forward(x)
    def backward(dy: X, y: X, x: X) = self.backward(dy, y, x)
  }

}
