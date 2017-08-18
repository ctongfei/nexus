package nexus.impl

import nexus._

/**
 * Environment for running untyped (NumPy/Torch/etc.) style NdArrays.
 * @tparam H Type of underlying untyped handle.
 * @author Tongfei Chen
 */
trait MathOps[_H, @specialized(Float, Double) _D] extends GradOps[_H] {

  type H = _H
  type D = _D

  def D: Field[D]

  def getScalar(x: H): D
  def scalar(x: D): H

  def zeroBy(x: H): H

  def add(x1: H, x2: H): H
  def addI(x1: H, x2: H): Unit
  def addS(x1: H, x2: D): H

  def sub(x1: H, x2: H): H
  def subS(x1: H, x2: D): H = addS(x1, D.negate(x2))

  def neg(x: H): H

  def eMul(x1: H, x2: H): H

  def eDiv(x1: H, x2: H): H


  def scale(x: _H, k: Double) = scale(x, D.fromDouble(k))
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


  def map(x: H)(f: D => D): H

  def map2(x1: H, x2: H)(f: (D, D) => D): H

  def map3(x1: H, x2: H, x3: H)(f: (D, D, D) => D): H

}
