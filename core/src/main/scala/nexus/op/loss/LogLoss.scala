package nexus.op.loss

import nexus._
import nexus.impl._

/**
 * The logarithmic loss (a.k.a. the cross entropy loss) function.
 *
 * The two inputs are
 *  - the predicted probability of labels (\(\mathbf{\hat y}\)), which should be a rank-1 tensor;
 *  - the gold labels (\(\mathbf{y}\)), which should be a rank-1 tensor.
 *
 * The output is the loss function value, which is a scalar value, computed as
 *
 * \( \mathscr{L}(\mathbf{\hat y}, \mathbf{y}) = -\sum_{i}{y_i \log \hat y_i} \).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object LogLoss extends PolyOp2[LogLossF]

@implicitNotFound("Cannot apply LogLoss to ${Ŷ} and ${Y}.")
trait LogLossF[Ŷ, Y, L] extends Op2[Ŷ, Y, L] {
  def name = "LogLoss"
}

object LogLossF {

  implicit def vector[T[_ <: $$], D, A](implicit ops: TypedMathOps[T, D]): LogLossF[T[A::$], T[A::$], T[$]] =
    new LogLossF[T[A::$], T[A::$], T[$]] {
      import ops._
      def _ops = ops.ground[$]
      def forward(ŷ: T[A::$], y: T[A::$]) =
        -(sum(y |*| log(ŷ)))
      def backward1(dl: T[$], l: T[$], ŷ: T[A::$], y: T[A::$]) =
        -(y |/| ŷ) :* dl
      def backward2(dl: T[$], l: T[$], ŷ: T[A::$], y: T[A::$]) =
        -log(ŷ) :* dl
    }
  
}
