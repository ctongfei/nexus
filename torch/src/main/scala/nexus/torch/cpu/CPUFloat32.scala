package nexus.torch.cpu

import nexus._
import nexus.torch._
import jtorch.th._

/**
 * @author Tongfei Chen
 */
object CPUFloat32 extends Env[DenseTensor, Float] {
  
  type Handle = THFloatTensor

  def field = algebra.instances.all.floatAlgebra
  def newTensor[A <: $$](axes: A, shape: Array[Int]) = ???
  def newGaussianTensor[A <: $$](μ: Double, σ2: Double, axes: A, shape: Array[Int]) = ???


  def untype(x: DenseTensor[Float, _]) = x.asInstanceOf[FloatTensor[_]].handle
  def typeOf[A <: $$](x: Tensor[A]) = x.axes
  def typeWith[A <: $$](x: THFloatTensor, a: A) = new FloatTensor[A](x, a)
  
  def fromDouble(d: Double) = d.toFloat
  def fromFloat(f: Float) = f
  
  def getScalar(x: THFloatTensor) = ???
  def scalar(x: Float) = ???
  def mapU(x: THFloatTensor)(f: (Float) => Float) = ???
  def zipWithU(x1: THFloatTensor, x2: THFloatTensor)(f: (Float, Float) => Float) = ???
  def zipWith3U(x1: THFloatTensor, x2: THFloatTensor, x3: THFloatTensor)(f: (Float, Float, Float) => Float) = ???
  def addInplace(x: THFloatTensor, d: THFloatTensor) = ???
  def addScalarU(x: THFloatTensor, u: Float) = ???
  def negU(x: THFloatTensor) = ???
  def addU(x: THFloatTensor, y: THFloatTensor) = ??? //TH.THFloatTensor_add(x, y, 1.0f)
  def subU(x: THFloatTensor, y: THFloatTensor) = ???
  def mulU(x: THFloatTensor, y: THFloatTensor) = ???
  def divU(x: THFloatTensor, y: THFloatTensor) = ???
  def scaleU(x: THFloatTensor, u: Float) = ???
  def invU(x: THFloatTensor) = ???
  def sqrU(x: THFloatTensor) = ???
  def sqrtU(x: THFloatTensor) = ???
  def transposeU(x: THFloatTensor) = ???
  def logU(x: THFloatTensor) = ???
  def expU(x: THFloatTensor) = ???
  def sigmoidU(x: THFloatTensor) = ???
  def reluU(x: THFloatTensor) = ???
  def posU(x: THFloatTensor) = ???
  def sumU(x: THFloatTensor) = ???
  def mvMulU(x: THFloatTensor, y: THFloatTensor) = ???
  def vvMulU(x: THFloatTensor, y: THFloatTensor) = ???
  def dotU(x: THFloatTensor, y: THFloatTensor) = ???
  def tMulU(x: THFloatTensor, y: THFloatTensor, matchedIndices: Seq[(Int, Int)]) = ???
  
}
