package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

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
object CrossEntropy extends VaVaSPolyDOp2 {
  def name = "CrossEntropy"
  def forward[T[_ <: $$], R, A](p: T[A::$], q: T[A::$])(implicit T: IsTypedRealTensor[T, R]) = {
    import T._
    -(T.sum(p |*| T.eLog(q)))
  }
  def backward1[T[_ <: $$], R, A](dy: R, y: R, p: T[A::$], q: T[A::$])(implicit T: IsTypedRealTensor[T, R]) =
    -T.eLog(q) :* dy
  def backward2[T[_ <: $$], R, A](dy: R, y: R, p: T[A::$], q: T[A::$])(implicit T: IsTypedRealTensor[T, R]) =
    -(p |/| q) :* dy
}

object KullbackLeiblerDivergence extends VaVaSPolyDOp2 {
  def name = "KullbackLeiblerDivergence"
  def forward[T[_ <: $$], R, A](p: T[A::$], q: T[A::$])(implicit T: IsTypedRealTensor[T, R]) = ???
  def backward1[T[_ <: $$], R, A](dy: R, y: R, p: T[A::$], q: T[A::$])(implicit T: IsTypedRealTensor[T, R]) = ???
  def backward2[T[_ <: $$], R, A](dy: R, y: R, p: T[A::$], q: T[A::$])(implicit T: IsTypedRealTensor[T, R]) = ???
}
