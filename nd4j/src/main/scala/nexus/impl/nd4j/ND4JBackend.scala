package nexus.impl.nd4j

import nexus.algebra._
import nexus.algebra.typelevel.util._
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.ops.transforms.Transforms

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
    x.handle.getFloat(x.index(is))
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
    new UntypedFloatTensor.Tensor(Nd4j.create(array, shape))

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
  def neg(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(x.handle.mul(-1))
  def add(x1: UntypedFloatTensor, x2: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(x1.handle.add(x2.handle.reshape(x1.handle.shape: _*)))
  def sub(x1: UntypedFloatTensor, x2: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(x1.handle.sub(x2.handle.reshape(x1.handle.shape: _*)))
  def eMul(x1: UntypedFloatTensor, x2: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(x1.handle.mul(x2.handle.reshape(x1.handle.shape: _*)))
  def eDiv(x1: UntypedFloatTensor, x2: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(x1.handle.div(x2.handle.reshape(x1.handle.shape: _*)))


  def addS(x1: UntypedFloatTensor, s: Double) =
    new UntypedFloatTensor.Tensor(x1.handle.add(s))

  def ePow(x: UntypedFloatTensor, p: Number) =
    new UntypedFloatTensor.Tensor(Transforms.pow(x.handle, p))

  def eSqr(x: UntypedFloatTensor) = ePow(x, 2)
  def eSqrt(x: UntypedFloatTensor) = ePow(x, 0.5)

  def eInv(x: UntypedFloatTensor) = ePow(x, -1)


  def scale(x: UntypedFloatTensor, u: Float) =
    new UntypedFloatTensor.Tensor(x.handle.mul(u))

  def addI(x: UntypedFloatTensor, d: UntypedFloatTensor): Unit =
    x.handle.addi(d.handle.reshape(x.handle.shape: _*))

  def unwrapScalar(x: UntypedFloatTensor) = x.handle.getFloat(0)
  def wrapScalar(x: Float): UntypedFloatTensor = FloatTensor.fill(x, (), Array())

  def addS(x: UntypedFloatTensor, u: Float) =
    new UntypedFloatTensor.Tensor(x.handle.add(u))

  def transpose(x: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(x.handle.transpose())


  def mmMul(x: UntypedFloatTensor, y: UntypedFloatTensor) = new UntypedFloatTensor.Tensor(x.handle.mmul(y.handle))

  def mvMul(x: UntypedFloatTensor, y: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(x.handle.mmul(y.handle.reshape(y.size, 1)))

  def vvMul(x: UntypedFloatTensor, y: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(x.handle.reshape(x.size, 1).mmul(y.handle.reshape(1, y.size)))


  def dot(x: UntypedFloatTensor, y: UntypedFloatTensor): R = sum(eMul(x, y))

  def eExp(x: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(Transforms.exp(x.handle))
  def eLog(x: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(Transforms.log(x.handle)) // TODO handle 0F values
  def eLog1p(x: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(Transforms.log(x.handle.add(1)))
  def eExpm1(x: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(Transforms.exp(x.handle).add(-1))

  def eSin(x: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(Transforms.sin(x.handle))
  def eCos(x: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(Transforms.cos(x.handle))
  def eTan(x: UntypedFloatTensor) = ???
  def eTanh(x: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(Transforms.tanh(x.handle))


  def eAbs(x: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(Transforms.abs(x.handle))
  def eSgn(x: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(Transforms.sign(x.handle))
  def eSigmoid(x: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(Transforms.sigmoid(x.handle))
  def eReLU(x: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(Transforms.relu(x.handle))
  def eIsPos(x: UntypedFloatTensor) =
    new UntypedFloatTensor.Tensor(x.handle.gt(0))

  def sum(x: UntypedFloatTensor) =
    x.handle.sum((0 until x.handle.rank): _*).getFloat(0)

  def tMul(x: UntypedFloatTensor, y: UntypedFloatTensor, matchedIndices: Seq[(Int, Int)]) = ???

  def expandDim(x: UntypedFloatTensor, i: Int) = ???
}

