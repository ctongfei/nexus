package nexus.impl

/**
 * Environment for running untyped (NumPy/Torch/etc.) style NdArrays.
 * @tparam H Type of underlying untyped handle.
 * @author Tongfei Chen
 */
trait UntypedRealTensorOps[H, @specialized(Float, Double) D] extends UntypedTensorOps[H, D] with GradOps[H] {

  def D: RealOps[D]

  def unwrapScalar(x: H): D
  def wrapScalar(x: D): H

  def zeroBy(x: H): H

  def add(x1: H, x2: H): H
  def addI(x1: H, x2: H): Unit
  def addS(x1: H, x2: D): H

  def sub(x1: H, x2: H): H
  def subS(x1: H, x2: D): H = addS(x1, D.negate(x2))

  def neg(x: H): H

  def eMul(x1: H, x2: H): H

  def eDiv(x1: H, x2: H): H

  def scale(x: H, k: Double) = scale(x, D.fromDouble(k))
  def scale(x: H, k: D): H

  def inv(x: H): H

  def sqr(x: H): H
  def sqrt(x: H): H

  def log(x: H): H
  def exp(x: H): H

  def sin(x: H): H
  def cos(x: H): H
  def tan(x: H): H

  def sum(x: H): H

  def sigmoid(x: H): H
  def reLU(x: H): H
  def isPos(x: H): H

  def transpose(x: H): H

  def mmMul(x: H, y: H): H
  def mvMul(x: H, y: H): H
  def vvMul(x: H, y: H): H
  def dot(x: H, y: H): H

  def tMul(x: H, y: H, matchedIndices: Seq[(Int, Int)]): H

}
