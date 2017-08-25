package nexus.op

import nexus._
import nexus.algebra._

/**
 * The cross entropy function.
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
object CrossEntropy extends PolyDOp2[CrossEntropyF]

@implicitNotFound("Cannot apply CrossEntropy to ${P} and ${Q}.")
trait CrossEntropyF[P, Q, Y] extends DOp2[P, Q, Y] {
  def name = "CrossEntropy"
}

object CrossEntropyF {

  implicit def vector[T[_ <: $$], D, A](implicit ops: TypedRealTensorOps[T, D]): CrossEntropyF[T[A::$], T[A::$], D] =
    new CrossEntropyF[T[A :: $], T[A :: $], D] {
      import ops._
      implicit val D = ops.R
      def gradOps = ops.R
      def forward(p: T[A :: $], q: T[A :: $]) =
        -(sum(p |*| log(q)))
      def backward1(dl: D, l: D, p: T[A :: $], q: T[A :: $]) =
        -log(q) :* dl
      def backward2(dl: D, l: D, p: T[A :: $], q: T[A :: $]) =
        -(p |/| q) :* dl
    }

}