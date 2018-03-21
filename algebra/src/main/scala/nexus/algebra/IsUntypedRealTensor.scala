package nexus.algebra

/**
 * Environment for running untyped (NumPy/Torch/etc.) style NdArrays.
 * @tparam T Type of underlying untyped handle
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait IsUntypedRealTensor[T, @specialized(Float, Double) R] extends IsUntypedTensor[T, R] with Grad[T] {

  val R: IsReal[R]

  def zeroBy(x: T): T

  def add(x1: T, x2: T): T
  def addI(x1: T, x2: T): Unit
  def addS(x1: T, x2: R): T

  def sub(x1: T, x2: T): T
  def subS(x1: T, x2: R): T = addS(x1, R.negate(x2))

  def neg(x: T): T

  def eMul(x1: T, x2: T): T

  def eDiv(x1: T, x2: T): T

  def scale(x: T, k: Double) = scale(x, R.fromDouble(k))
  def scale(x: T, k: R): T

  def eInv(x: T): T

  def eSqr(x: T): T
  def eSqrt(x: T): T

  def eLog(x: T): T
  def eExp(x: T): T
  def eLog1p(x: T): T
  def eExpm1(x: T): T

  def eSin(x: T): T
  def eCos(x: T): T
  def eTan(x: T): T

  def sum(x: T): R

  def eSigmoid(x: T): T
  def eReLU(x: T): T

  def eAbs(x: T): T
  def eSgn(x: T): T
  def eIsPos(x: T): T

  def transpose(x: T): T

  def mmMul(x: T, y: T): T
  def mvMul(x: T, y: T): T
  def vvMul(x: T, y: T): T
  def dot(x: T, y: T): R

  def tMul(x: T, y: T, matchedIndices: Seq[(Int, Int)]): T

}
