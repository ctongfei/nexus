package nexus.impl.nd4j

import nexus.algebra._
import nexus.algebra.typelevel.util._
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.ops.transforms.Transforms
import org.nd4s.Implicits._

import scala.util._


/**
  * @author Andrey Romanov
  */
object ND4JBackendTensor extends IsRealTensorK[FloatTensor, Float]{
  type H = UntypedFloatTensor
  val H = UntypedND4JBackendTensor

  val R = H.R

  def newTensor[A](shape: Array[Int]) =
    FloatTensor.fromFlatArray[A](Array.ofDim[Float](ShapeUtils.product(shape)), shape)

  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]) = {
    val r = new Random()
    val σ = math.sqrt(σ2)
    FloatTensor.fromFlatArray(Array.fill(ShapeUtils.product(shape))(((r.nextGaussian() - μ) * σ).toFloat), shape)
  }

  def get(x: FloatTensor[_], is: Array[Int]): Float = {
    x.handle(x.index(is)).toFloat
  }
  def set(x: FloatTensor[_], is: Array[Int], v: Float) = x.update(is: _*)(v)
  def zeroBy[A](x: FloatTensor[A]) = newTensor(x.shape)

  def untype(x: FloatTensor[_]) = x
  def typeWith[A](x: UntypedFloatTensor) = x.typeWith[A]

  def fromDouble(d: Double) = d.toFloat
  def fromFloat(f: Float) = f
}


object UntypedND4JBackendTensor extends IsUntypedRealTensor[UntypedFloatTensor, Float] {

  type R = Float
  val R = nexus.algebra.instances.Float32

  def fromFlatArray(array: Array[R], shape: Array[Int]) =
    new UntypedFloatTensor.Tensor(array.asNDArray(shape: _*))

  def mutable = true

  def rank(x: UntypedFloatTensor) = x.rank
  def shape(x: UntypedFloatTensor) = x.shape
  def slice(x: UntypedFloatTensor, dim: Int, i: Int) = x.sliceUntyped(dim, i)


  /*
  we could use nd4s collection-like ops but it's broken
  https://github.com/deeplearning4j/nd4s/issues/91
   */
  def map(x: UntypedFloatTensor)(f: Float => Float): UntypedFloatTensor = {
    ???
  }

  def map2(x1: UntypedFloatTensor, x2: UntypedFloatTensor)(f: (Float, Float) => Float): UntypedFloatTensor = {
    ???
  }

  def map3(x1: UntypedFloatTensor, x2: UntypedFloatTensor, x3: UntypedFloatTensor)(f: (Float, Float, Float) => Float): UntypedFloatTensor = {
    ???
  }

  def inplace2(x1: UntypedFloatTensor, x2: UntypedFloatTensor)(f: (Float, Float) => Float): Unit = {
    ???
  }


  def zeroBy(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(Nd4j.zeros(x.shape: _*))


  // Here and after we rely on build in Nd4j Ops instead of inefficient `map/map2/inplace2` implementation
  def neg(x: UntypedFloatTensor) =  new UntypedFloatTensor.Tensor(-x.handle)
  def add(x1: UntypedFloatTensor, x2: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(x1.handle + x2.handle)
  def sub(x1: UntypedFloatTensor, x2: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(x1.handle - x2.handle)
  def eMul(x1: UntypedFloatTensor, x2: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(x1.handle * x2.handle)
  def eDiv(x1: UntypedFloatTensor, x2: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(x1.handle / x2.handle)


  def addS(x1: UntypedFloatTensor, s: Double) = new UntypedFloatTensor.Tensor(x1.handle + s)

  def ePow(x: UntypedFloatTensor, p: Number) = new UntypedFloatTensor.Tensor(Transforms.pow(x.handle, p))

  def eSqr(x: UntypedFloatTensor) = ePow(x, 2)
  def eSqrt(x: UntypedFloatTensor) = ePow(x, 0.5)

  def eInv(x: UntypedFloatTensor) = ePow(x, -1)


  def scale(x: UntypedFloatTensor, u: Float) = new UntypedFloatTensor.Tensor(x.handle * u)

  def addI(x: UntypedFloatTensor, d: UntypedFloatTensor): Unit = x.handle.addi(d.handle)


  def unwrapScalar(x: UntypedFloatTensor) = x.handle(0).toFloat
  def wrapScalar(x: Float): UntypedFloatTensor = FloatTensor.fill(x, (), Array())

  def addS(x: UntypedFloatTensor, u: Float) = new UntypedFloatTensor.Tensor(x.handle + u)

  def transpose(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(x.handle.T)


  def mmMul(x: UntypedFloatTensor, y: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(x.handle.dot(y.handle))

  def mvMul(x: UntypedFloatTensor, y: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(x.handle.dot(y.handle))

  def vvMul(x: UntypedFloatTensor, y: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(x.handle.T.dot(y.handle))


  def dot(x: UntypedFloatTensor, y: UntypedFloatTensor): R = sum(eMul(x, y))

  def eExp(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(Transforms.exp(x.handle))
  def eLog(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(Transforms.log(x.handle)) // TODO handle 0F values
  def eLog1p(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(Transforms.log(x.handle + 1))
  def eExpm1(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(Transforms.exp(x.handle) - 1)

  def eSin(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(Transforms.sin(x.handle))
  def eCos(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(Transforms.cos(x.handle))
  def eTan(x: UntypedFloatTensor) = ???
  def eTanh(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(Transforms.tanh(x.handle))


  def eAbs(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(Transforms.abs(x.handle))
  def eSgn(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(Transforms.sign(x.handle))
  def eSigmoid(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(Transforms.sigmoid(x.handle))
  def eReLU(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(Transforms.relu(x.handle))
  def eIsPos(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(x.handle.gt(0))

  def sum(x: UntypedFloatTensor) = x.handle.sum((0 until x.handle.rank): _*)(0).toFloat

  def tMul(x: UntypedFloatTensor, y: UntypedFloatTensor, matchedIndices: Seq[(Int, Int)]) = ???

  def expandDim(x: UntypedFloatTensor, i: Int) = ???
}

