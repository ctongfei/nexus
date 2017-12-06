package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.op.base._

/**
 * Square of a number.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sqr extends TypeInvariantPolyDOp1[IsReal] {
  def name = "Sqr"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.sqr(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * x * 2

  /**
   * Elementwise square.
   */
  object Elementwise extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
    def name = "Sqr.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, R]) = T.eSqr(x)
    def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy |*| x :* 2
  }
}


object Sqrt extends TypeInvariantPolyDOp1[IsReal] {
  def name = "Sqrt"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.sqrt(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.inv(y) * 0.5

  object Elementwise extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
    def name = "Sqrt.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, R]) = T.eSqrt(x)
    def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy |*| T.eInv(y) :* 0.5
  }
}

