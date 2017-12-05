package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * Absolute value.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Abs extends TypeInvariantPolyDOp1[IsReal] {

  def name = "Abs"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.abs(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.sgn(x)

  object Elementwise extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
    def name = "Abs.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, R]) = T.eAbs(x)
    def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy |*| T.eSgn(x)
  }

}

/**
 * 「L_1」 (Manhattan) normalization.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object L1Normalize extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
  def name = "L1Normalize"
  def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, R]) = x :* T.R.inv(T.sum(x))
  def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, R]) = ???
}

/**
 * 「L_2」 (Euclidean) distance between two vectors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object L2Distance extends VaVaSPolyDOp2 {
  def name = "L2Distance"
  def forward[T[_ <: $$], R, A](x1: T[::[A, $]], x2: T[::[A, $]])(implicit T: IsTypedRealTensor[T, R]) = T.R.eSqrt(T.sum(T.eSqr(x1 - x2)))
  def backward1[T[_ <: $$], R, A](dy: R, y: R, x1: T[::[A, $]], x2: T[::[A, $]])(implicit T: IsTypedRealTensor[T, R]) = (x1 - x2) :* (dy / y)
  def backward2[T[_ <: $$], R, A](dy: R, y: R, x1: T[::[A, $]], x2: T[::[A, $]])(implicit T: IsTypedRealTensor[T, R]) = (x2 - x1) :* (dy / y)
}

object CosineSimilarity extends VaVaSPolyDOp2 {
  def name = "CosineSimilarity"
  def forward[T[_ <: $$], R, A](x1: T[::[A, $]], x2: T[::[A, $]])(implicit T: IsTypedRealTensor[T, R]) = ???
  def backward1[T[_ <: $$], R, A](dy: R, y: R, x1: T[::[A, $]], x2: T[::[A, $]])(implicit T: IsTypedRealTensor[T, R]) = ???
  def backward2[T[_ <: $$], R, A](dy: R, y: R, x1: T[::[A, $]], x2: T[::[A, $]])(implicit T: IsTypedRealTensor[T, R]) = ???
}
