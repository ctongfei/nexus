package nexus.diff.ops

import nexus.diff._
import nexus.diff.execution._
import nexus.diff.exception._
import nexus._

/**
 * Conditional expression.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object If extends PolyOp3 {

  implicit def ifF[X](implicit X: Grad[X]): P[Boolean, X, X, X] = new P[Boolean, X, X, X] {
      def name = "If"
      def tag = Tag.of(X)
      def forward(c: Boolean, t: X, f: X) = if (c) t else f
      def backward1(dy: X, y: X, c: Boolean, t: X, f: X) = throw new OperatorNotDifferentiableException(this, 1)
      def backward2(dy: X, y: X, c: Boolean, t: X, f: X) = if (c) dy else X.zeroBy(t)
      def backward3(dy: X, y: X, c: Boolean, t: X, f: X) = if (!c) dy else X.zeroBy(f)
  }

  /**
   * @note Known in other libraries as `torch.where` / `tf.cond`.
   */
  object Elementwise extends PolyOp3 {

    implicit def ifElementwiseF[TB[_], B, TR[_], R, U](
                                                        implicit TB: BoolTensorK[TB, B],
                                                        TR: IsRealTensorK[TR, R]
                                                      ): P[TB[U], TR[U], TR[U], TR[U]] =
      new P[TB[U], TR[U], TR[U], TR[U]] {
        def name = "If.Elementwise"
        def tag = Tag.realTensor[TR, R, U]
        def forward(x1: TB[U], x2: TR[U], x3: TR[U]) = ???
        def backward1(dy: TR[U], y: TR[U], x1: TB[U], x2: TR[U], x3: TR[U]) = throw new OperatorNotDifferentiableException(this, 1)
        def backward2(dy: TR[U], y: TR[U], x1: TB[U], x2: TR[U], x3: TR[U]) = ???
        def backward3(dy: TR[U], y: TR[U], x1: TB[U], x2: TR[U], x3: TR[U]) = ???
      }

  }

}


/**
 * Identity function for any expression, but stops gradient backpropagation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object StopGrad extends PolyOp1 {

  implicit def stopGradF[X]: P[X, X] =
    new P[X, X] {
      def name = "StopGrad"
      def tag = Tag.none[X]
      override def differentiable = false
      def forward(x: X) = x
      def backward(dy: X, y: X, x: X) = throw new OperatorNotDifferentiableException(this, 1)
  }

}
