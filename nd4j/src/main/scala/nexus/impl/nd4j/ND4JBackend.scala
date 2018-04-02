package nexus.impl.nd4j

import nexus.algebra._
import nexus.algebra.typelevel.util._
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.ops.transforms.Transforms

import scala.util._


/**
  * @author Andrey Romanov
  */
object ND4JTensor extends IsRealTensorK[FloatTensor, Float]{
  type H = INDArray
  val H = UntypedND4JTensor

  val R = H.R

  def newTensor[A](shape: Array[Int]) =
    FloatTensor.fromFlatArray[A](Array.ofDim[Float](ShapeUtils.product(shape)), shape)

  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]) = {
    val r = new Random()
    val σ = math.sqrt(σ2)
    FloatTensor.fromFlatArray(Array.fill(ShapeUtils.product(shape))(((r.nextGaussian() - μ) * σ).toFloat), shape)
  }

  def get(x: FloatTensor[_], is: Array[Int]): Float = {
    x.handle.getFloat(x.index(is))
  }
  def set(x: FloatTensor[_], is: Array[Int], v: Float) = x.update(is: _*)(v)
  def zeroBy[A](x: FloatTensor[A]) = newTensor(x.shape)

  def untype(x: FloatTensor[_]) = x.handle
  def typeWith[A](x: INDArray) = new FloatTensor.Tensor(x)

  def fromDouble(d: Double) = d.toFloat
  def fromFloat(f: Float) = f
}


object UntypedND4JTensor extends IsUntypedRealTensor[INDArray, Float] {

  type R = Float
  val R = nexus.algebra.instances.Float32

  def fromFlatArray(array: Array[R], shape: Array[Int]) = Nd4j.create(array, shape)

  def mutable = true

  def rank(x: INDArray) = x.rank
  def shape(x: INDArray) = x.shape
  def slice(x: INDArray, dim: Int, i: Int) = x.slice(i, dim)

  /*
  we could use nd4s collection-like ops but it's broken
  https://github.com/deeplearning4j/nd4s/issues/91
   */
  def map(x: INDArray)(f: Float => Float): INDArray = {
    ???
  }

  def map2(x1: INDArray, x2: INDArray)(f: (Float, Float) => Float): INDArray = {
    ???
  }

  def map3(x1: INDArray, x2: INDArray, x3: INDArray)(f: (Float, Float, Float) => Float): INDArray = {
    ???
  }

  def inplace2(x1: INDArray, x2: INDArray)(f: (Float, Float) => Float): Unit = {
    ???
  }

  def zeroBy(x: INDArray) = Nd4j.zeros(x.shape: _*)

  // Here and after we rely on build in Nd4j Ops instead of `map/map2/inplace2` implementations
  def neg(x: INDArray) = x.mul(-1)
  // TODO remove `reshape`s to handle dimentions properly
  def add(x1: INDArray, x2: INDArray) = x1.add(x2.reshape(x1.shape: _*))
  def sub(x1: INDArray, x2: INDArray) = x1.sub(x2.reshape(x1.shape: _*))
  def eMul(x1: INDArray, x2: INDArray) = x1.mul(x2.reshape(x1.shape: _*))
  def eDiv(x1: INDArray, x2: INDArray) = x1.div(x2.reshape(x1.shape: _*))

  def addS(x1: INDArray, s: Double) = x1.add(s)

  def ePow(x: INDArray, p: Number) = Transforms.pow(x, p)

  def eSqr(x: INDArray) = ePow(x, 2)
  def eSqrt(x: INDArray) = ePow(x, 0.5)

  def eInv(x: INDArray) = ePow(x, -1)

  def scale(x: INDArray, u: Float) = x.mul(u)

  def addI(x: INDArray, d: INDArray): Unit = x.addi(d.reshape(x.shape: _*))

  def unwrapScalar(x: INDArray) = x.getFloat(0)
  def wrapScalar(x: Float): INDArray = Nd4j.ones(1, 1).mul(x)

  def addS(x: INDArray, u: Float) = x.add(u)

  def transpose(x: INDArray) = x.transpose()

  def mmMul(x: INDArray, y: INDArray) = x.mmul(y)

  def mvMul(x: INDArray, y: INDArray) = x.mmul(y.reshape(y.length, 1))

  def vvMul(x: INDArray, y: INDArray) = x.reshape(x.length, 1).mmul(y.reshape(1, y.length))


  def dot(x: INDArray, y: INDArray): R = sum(eMul(x, y))

  def eExp(x: INDArray) = Transforms.exp(x)
  def eLog(x: INDArray) = Transforms.log(x) // TODO handle 0F values
  def eLog1p(x: INDArray) = Transforms.log(x.add(1))
  def eExpm1(x: INDArray) = Transforms.exp(x).add(-1)

  def eSin(x: INDArray) = Transforms.sin(x)
  def eCos(x: INDArray) = Transforms.cos(x)
  def eTan(x: INDArray) = ???

  def eAbs(x: INDArray) = Transforms.abs(x)
  def eSgn(x: INDArray) = Transforms.sign(x)
  def eSigmoid(x: INDArray) = Transforms.sigmoid(x)
  def eReLU(x: INDArray) = Transforms.relu(x)
  def eIsPos(x: INDArray) = x.gt(0)

  def sum(x: INDArray) = x.sum((0 until x.rank): _*).getFloat(0)

  def tMul(x: INDArray, y: INDArray, matchedIndices: Seq[(Int, Int)]) = ???

  def expandDim(x: INDArray, i: Int) = ???
}

