package nexus.func.factory

import nexus._
import nexus.algebra._

import scala.annotation._

/**
 * @author Tongfei Chen
 */
abstract class TypeInvariantDOp2Factory[Ev[X] <: Grad[X]] { self =>

  def name: String

  def forward[X](x1: X, x2: X)(implicit X: Ev[X]): X

  def backward1[X](dy: X, y: X, x1: X, x2: X)(implicit X: Ev[X]): X

  def backward2[X](dy: X, y: X, x1: X, x2: X)(implicit X: Ev[X]): X

  @implicitNotFound("Cannot apply this operator to ${X1} and ${X2}.")
  trait Op[X1, X2, Y] extends DOp2[X1, X2, Y]

  object Op {
    implicit def synthesize[X](implicit X: Ev[X]) = new OpImpl[X]
  }

  class OpImpl[X](implicit X: Ev[X]) extends Op[X, X, X] {
    def name = self.name
    def tag = X
    def forward(x1: X, x2: X) = self.forward(x1, x2)
    def backward1(dy: X, y: X, x1: X, x2: X) = self.backward1(dy, y, x1, x2)
    def backward2(dy: X, y: X, x1: X, x2: X) = self.backward2(dy, y, x1, x2)
  }

}
