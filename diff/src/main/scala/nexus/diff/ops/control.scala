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
object Cond extends PolyOp3 {

  implicit def condF[X](implicit X: Grad[X]): F[Boolean, X, X, X] = new F[Boolean, X, X, X] {
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

    implicit def condElementwiseF[TB[_], B, TR[_], R, a](
                                                        implicit TB: BoolTensorK[TB, B],
                                                        TR: IsRealTensorK[TR, R]
                                                      ): F[TB[a], TR[a], TR[a], TR[a]] =
      new F[TB[a], TR[a], TR[a], TR[a]] {
        def name = "If.Elementwise"
        def tag = Tag.realTensor[TR, R, a]
        def forward(x1: TB[a], x2: TR[a], x3: TR[a]) = ???
        def backward1(dy: TR[a], y: TR[a], x1: TB[a], x2: TR[a], x3: TR[a]) = throw new OperatorNotDifferentiableException(this, 1)
        def backward2(dy: TR[a], y: TR[a], x1: TB[a], x2: TR[a], x3: TR[a]) = ???
        def backward3(dy: TR[a], y: TR[a], x1: TB[a], x2: TR[a], x3: TR[a]) = ???
      }

  }

}


/**
 * Identity function for any expression, but stops gradient backpropagation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object StopGrad extends PolyOp1 {

  implicit def stopGradF[X]: F[X, X] =
    new F[X, X] {
      def name = "StopGrad"
      def tag = Tag.any[X]
      override def differentiable = false
      def forward(x: X) = x
      def backward(dy: X, y: X, x: X) = throw new OperatorNotDifferentiableException(this, 1)
  }

}
