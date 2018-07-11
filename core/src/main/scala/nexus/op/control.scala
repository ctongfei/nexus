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

  /**
   * @note Known in other libraries as `torch.where`.
   */
  object Elementwise extends PolyOp3 {

    implicit def ifElementwiseF[TB[_], B, TR[_], R, A](
                                                        implicit TB: IsBoolTensorK[TB, B],
                                                        TR: IsRealTensorK[T, R]
                                                      ): F[TB[A], TR[A], TR[A], TR[A]]
    = new F[TB[A], TR[A], TR[A], TR[A]] {
      def name = "If.Elementwise"
      def tag(tx1: Type[TB[A]], tx2: Type[TR[A]], tx3: Type[TR[A]]) = ???
      def forward(x1: TB[A], x2: TR[A], x3: TR[A]) = ???
      def backward1(dy: TR[A], y: TR[A], x1: TB[A], x2: TR[A], x3: TR[A]) = ???
      def backward2(dy: TR[A], y: TR[A], x1: TB[A], x2: TR[A], x3: TR[A]) = ???
      def backward3(dy: TR[A], y: TR[A], x1: TB[A], x2: TR[A], x3: TR[A]) = ???
    }

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
