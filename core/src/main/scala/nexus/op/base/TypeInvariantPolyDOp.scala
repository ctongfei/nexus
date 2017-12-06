package nexus.op.base

import nexus.algebra._
import nexus.{DOp1, DOp2, PolyDOp1, PolyDOp2}

import scala.annotation._

/**
 * Represents a polymorphic function that has the type
 * (implicit Ev[X]) => X ~> X
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class TypeInvariantPolyDOp1[Ev[X] <: Grad[X]] extends PolyDOp1 { self =>

  def name: String

  def forward[R](x: R)(implicit R: Ev[R]): R

  def backward[R](dy: R, y: R, x: R)(implicit R: Ev[R]): R

  @implicitNotFound("Cannot apply this operator to ${X}.")
  trait DOp[X, Y] extends DOp1[X, Y]

  object DOp {
    /**
     * Synthesizes an operator for type [[X]].
     */
    implicit def synthesize[X](implicit X: Ev[X]): Op[X, X] = new OpImpl[X]
  }

  class OpImpl[X](implicit X: Ev[X]) extends DOp[X, X] {
    def tag = X
    def name = self.name
    def forward(x: X) = self.forward(x)
    def backward(dy: X, y: X, x: X) = self.backward(dy, y, x)
  }

}

abstract class TypeInvariantPolyDOp2[Ev[X] <: Grad[X]] extends PolyDOp2 { self =>

  def name: String

  def forward[R](x1: R, x2: R)(implicit R: Ev[R]): R
  def backward1[R](dy: R, y: R, x1: R, x2: R)(implicit R: Ev[R]): R
  def backward2[R](dy: R, y: R, x1: R, x2: R)(implicit R: Ev[R]): R

  @implicitNotFound("Cannot apply this operator to ${X1} and ${X2}.")
  trait DOp[X1, X2, Y] extends DOp2[X1, X2, Y]

  object DOp {
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