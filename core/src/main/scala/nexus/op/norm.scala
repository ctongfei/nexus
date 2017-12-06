package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.op.base._

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

object L1Norm extends TaSPolyDOp1 {
  def name = "L1Norm"
  def forward[T[_ <: $$], R, As <: $$](x: T[As])(implicit T: IsTypedRealTensor[T, R]) = T.sum(T.eAbs(x))
  def backward[T[_ <: $$], R, As <: $$](dy: R, y: R, x: T[As])(implicit T: IsTypedRealTensor[T, R]) = T.eSgn(x) :* dy
}

object L2Norm extends TaSPolyDOp1 {
  def name = "L2Norm"
  def forward[T[_ <: $$], R, As <: $$](x: T[As])(implicit T: IsTypedRealTensor[T, R]) = T.R.sqrt(T.dot(x, x))
  def backward[T[_ <: $$], R, As <: $$](dy: R, y: R, x: T[As])(implicit T: IsTypedRealTensor[T, R]) = x :* (dy / y)
}

