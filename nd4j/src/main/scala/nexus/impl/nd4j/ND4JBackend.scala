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
object ND4JBackendTensor extends IsRealTensorK[ND4JTensor, Float]{
  type H = UntypedND4JTensor
  val H = UntypedND4JBackendTensor

  val R = H.R

  def newTensor[A](shape: Array[Int]) =
    ND4JTensor.fromFlatArray[A](Array.ofDim[Float](ShapeUtils.product(shape)), shape)

  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]) = {
    val r = new Random()
    val σ = math.sqrt(σ2)
    ND4JTensor.fromFlatArray(Array.fill(ShapeUtils.product(shape))(((r.nextGaussian() - μ) * σ).toFloat), shape)
  }

  def get(x: ND4JTensor[_], is: Array[Int]): Float = {
    x.handle(x.index(is)).toFloat
  }
  def set(x: ND4JTensor[_], is: Array[Int], v: Float) = x.update(is: _*)(v)
  def zeroBy[A](x: ND4JTensor[A]) = newTensor(x.shape)

  def untype(x: ND4JTensor[_]) = x
  def typeWith[A](x: UntypedND4JTensor) = x.typeWith[A]

  def fromDouble(d: Double) = d.toFloat
  def fromFloat(f: Float) = f
}


object UntypedND4JBackendTensor extends IsUntypedRealTensor[UntypedND4JTensor, Float] {

  type R = Float
  val R = nexus.algebra.instances.Float32

  def fromFlatArray(array: Array[R], shape: Array[Int]) =
    new UntypedND4JTensor.Tensor(array.asNDArray(shape: _*))

  def mutable = true

  def rank(x: UntypedND4JTensor) = x.rank
  def shape(x: UntypedND4JTensor) = x.shape
  def slice(x: UntypedND4JTensor, dim: Int, i: Int) = x.sliceUntyped(dim, i)


  /*
  we could use nd4s collection-like ops but it's broken
  https://github.com/deeplearning4j/nd4s/issues/91
   */
  def map(x: UntypedND4JTensor)(f: Float => Float): UntypedND4JTensor = {
    ???
  }

  def map2(x1: UntypedND4JTensor, x2: UntypedND4JTensor)(f: (Float, Float) => Float): UntypedND4JTensor = {
    ???
  }

  def map3(x1: UntypedND4JTensor, x2: UntypedND4JTensor, x3: UntypedND4JTensor)(f: (Float, Float, Float) => Float): UntypedND4JTensor = {
    ???
  }

  def inplace2(x1: UntypedND4JTensor, x2: UntypedND4JTensor)(f: (Float, Float) => Float): Unit = {
    ???
  }


  def zeroBy(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(Nd4j.zeros(x.shape: _*))


  // Here and after we rely on build in Nd4j Ops instead of inefficient `map/map2/inplace2` implementation
  def neg(x: UntypedND4JTensor) =  new UntypedND4JTensor.Tensor(-x.handle)
  def add(x1: UntypedND4JTensor, x2: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(x1.handle + x2.handle)
  def sub(x1: UntypedND4JTensor, x2: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(x1.handle - x2.handle)
  def eMul(x1: UntypedND4JTensor, x2: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(x1.handle * x2.handle)
  def eDiv(x1: UntypedND4JTensor, x2: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(x1.handle / x2.handle)


  def addS(x1: UntypedND4JTensor, s: Double) = new UntypedND4JTensor.Tensor(x1.handle + s)

  def ePow(x: UntypedND4JTensor, p: Number) = new UntypedND4JTensor.Tensor(Transforms.pow(x.handle, p))

  def eSqr(x: UntypedND4JTensor) = ePow(x, 2)
  def eSqrt(x: UntypedND4JTensor) = ePow(x, 0.5)

  def eInv(x: UntypedND4JTensor) = ePow(x, -1)


  def scale(x: UntypedND4JTensor, u: Float) = new UntypedND4JTensor.Tensor(x.handle * u)

  def addI(x: UntypedND4JTensor, d: UntypedND4JTensor): Unit = x.handle.addi(d.handle)


  def unwrapScalar(x: UntypedND4JTensor) = x.handle(0).toFloat
  def wrapScalar(x: Float): UntypedND4JTensor = ND4JTensor.fill(x, (), Array())

  def addS(x: UntypedND4JTensor, u: Float) = new UntypedND4JTensor.Tensor(x.handle + u)

  def transpose(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(x.handle.T)


  def mmMul(x: UntypedND4JTensor, y: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(x.handle.dot(y.handle))

  def mvMul(x: UntypedND4JTensor, y: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(x.handle.dot(y.handle))

  def vvMul(x: UntypedND4JTensor, y: UntypedND4JTensor) =
    new UntypedND4JTensor.Tensor(x.handle.T.dot(y.handle))


  def dot(x: UntypedND4JTensor, y: UntypedND4JTensor): R = sum(eMul(x, y))

  def eExp(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(Transforms.exp(x.handle))
  def eLog(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(Transforms.log(x.handle)) // TODO handle 0F values
  def eLog1p(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(Transforms.log(x.handle + 1))
  def eExpm1(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(Transforms.exp(x.handle) - 1)

  def eSin(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(Transforms.sin(x.handle))
  def eCos(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(Transforms.cos(x.handle))
  def eTan(x: UntypedND4JTensor) = ???
  def eTanh(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(Transforms.tanh(x.handle))


  def eAbs(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(Transforms.abs(x.handle))
  def eSgn(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(Transforms.sign(x.handle))
  def eSigmoid(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(Transforms.sigmoid(x.handle))
  def eReLU(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(Transforms.relu(x.handle))
  def eIsPos(x: UntypedND4JTensor) = new UntypedND4JTensor.Tensor(x.handle.gt(0))

  def sum(x: UntypedND4JTensor) = x.handle.sum((0 until x.handle.rank): _*)(0).toFloat

  def tMul(x: UntypedND4JTensor, y: UntypedND4JTensor, matchedIndices: Seq[(Int, Int)]) = ???

  def expandDim(x: UntypedND4JTensor, i: Int) = ???
}

