package nexus.op

import nexus._
import nexus.algebra._
import nexus.execution._
import nexus.exception._

/**
 * Conditional expression.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object If extends PolyOp3 {

  implicit def ifF[X](implicit X: Grad[X]): F[Boolean, X, X, X] = new F[Boolean, X, X, X] {
    def name = "If"
    def tag(tx1: Type[Boolean], tx2: Type[X], tx3: Type[X]) = tx2
    def forward(c: Boolean, t: X, f: X) = if (c) t else f
    def backward1(dy: X, y: X, c: Boolean, t: X, f: X) = throw new OperatorNotDifferentiableException(name, 1)
    def backward2(dy: X, y: X, c: Boolean, t: X, f: X) = if (c) dy else X.zeroBy(t)
    def backward3(dy: X, y: X, c: Boolean, t: X, f: X) = if (!c) dy else X.zeroBy(f)
  }

}

/**
 * Identity function for any expression, but stops gradient backpropagation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object StopGrad extends PolyOp1 {
  implicit def stopGradF[X]: F[X, X] = new F[X, X] {
    def name = "StopGrad"
    def tag(tx: Type[X]) = tx
    override def differentiable = false
    def forward(x: X) = x
    def backward(dy: X, y: X, x: X) = throw new OperatorNotDifferentiableException(name, 1)
  }
}
