package nexus.op.loss

import nexus._

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

@impMsg("Cannot apply LogLoss to ${YP} and ${YG}.")
trait LogLossF[YP, YG, L] extends Op2[YP, YG, L] {
  def name = "LogLoss"
}

object LogLossF {

  implicit def LogLossImpl[T[_, _ <: $$], D, A](implicit env: Env[T, D]): LogLossF[T[D, A::$], T[D, A::$], T[D, $]] =
    new LogLossF[T[D, A::$], T[D, A::$], T[D, $]] {
      import env._
      def forward(yp: T[D, A::$], yg: T[D, A::$]) =
        -(sum(yg |*| log(yp)))
      def backward1(dl: T[D, $], l: T[D, $], yp: T[D, A::$], yg: T[D, A::$]) = {
         -(yg |/| yp)
      } // should times dl
      def backward2(dl: T[D, $], l: T[D, $], yp: T[D, A::$], yg: T[D, A::$]) = -log(yp) // should times dl
    }
  
}
