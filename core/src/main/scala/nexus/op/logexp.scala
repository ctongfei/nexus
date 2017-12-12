package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.op.base._

/**
 * Exponentiation of a real number.
 * - Input: A real number \(x\).
 * - Output: A real number \(y\) computed as \(y = e^x\).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Exp extends TypeInvariantPolyDOp1[IsReal] {

  def name = "Exp"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.exp(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * y

  /**
   * Element-wise exponentiation.
   *
   * Input: Any tensor 「bb"x"」 with axes 「i_1 , ... , i_d」.
   *
   * Output: A tensor 「bb"y"」 with the same shape as 「bb"x"」, computed as
   * 「y_(i_1, ..., i_d) = exp(x_(i_1, ..., i_d))」.
   *
   * @author Tongfei Chen
   * @since 0.1.0
   */
  object Elementwise extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
    def name = "Exp.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, R]) = T.eExp(x)
    def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy |*| y
  }
}

/**
 * Element-wise natural logarithm.
 * - Input: A real number \(x\).
 *
 */
object Log extends TypeInvariantPolyDOp1[IsReal] {
  def name = "Log"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.log(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy / x

  object Elementwise extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
    def name = "Log.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, R]) = T.eLog(x)
    def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy |/| x
  }
}
