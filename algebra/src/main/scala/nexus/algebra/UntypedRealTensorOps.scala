package nexus.algebra

/**
 * Environment for running untyped (NumPy/Torch/etc.) style NdArrays.
 * @tparam H Type of underlying untyped handle
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait UntypedRealTensorOps[H, @specialized(Float, Double) R] extends UntypedTensorOps[H, R] with GradOps[H] {

  val R: RealOps[R]

  def unwrapScalar(x: H): R
  def wrapScalar(x: R): H

  def zeroBy(x: H): H

  def add(x1: H, x2: H): H
  def addI(x1: H, x2: H): Unit
  def addS(x1: H, x2: R): H

  def sub(x1: H, x2: H): H
  def subS(x1: H, x2: R): H = addS(x1, R.negate(x2))

  def neg(x: H): H

  def eMul(x1: H, x2: H): H

  def eDiv(x1: H, x2: H): H

  def scale(x: H, k: Double) = scale(x, R.fromDouble(k))
  def scale(x: H, k: R): H

  def eInv(x: H): H

  def eSqr(x: H): H
  def eSqrt(x: H): H

  def log(x: H): H
  def exp(x: H): H
  def log1p(x: H): H
  def expm1(x: H): H

  def sin(x: H): H
  def cos(x: H): H
  def tan(x: H): H

  def sum(x: H): R

  def sigmoid(x: H): H
  def reLU(x: H): H
  def isPos(x: H): H

  def transpose(x: H): H

  def mmMul(x: H, y: H): H
  def mvMul(x: H, y: H): H
  def vvMul(x: H, y: H): H
  def dot(x: H, y: H): R

  def tMul(x: H, y: H, matchedIndices: Seq[(Int, Int)]): H

}
