package nexus.torch

import nexus._
import nexus.instances._
import nexus.typelevel._
import nexus.util._
import nexus.torch.macros._
import nexus.torch.jni.torchJNI._
import shapeless._
import scala.reflect._

/**
 * Torch-backed CPU float tensor.
 * @tparam A Axis descriptor
 */
class FloatTensor[A] private[torch](ptr: Long) extends Tensor[Float, A](ptr) {

  import FloatTensorIsRealTensorK._

  def storage = new Storage[Float](ptr = THFloatTensor_storage(ptr), tag = Storage.TypeTag.Float)
  def tensorRank = THFloatTensor_nDimension(ptr)

  def free(): Unit = THFloatTensor_free(ptr)

  override def toString =
    s"FloatTensor[@0x${ptr.toHexString}; shape = ${StringUtils.arraySummary(shape(this))}]"
}

object FloatTensor extends TensorFactory[FloatTensor, Float](FloatTensorIsRealTensorK)

object FloatTensorIsRealTensorK extends IsRealTensorK[FloatTensor, Float] {

  import scala.language.experimental.macros

  implicit val R: IsReal[Float] = FloatIsReal
  def elementClassTag = ClassTag.Float

  def ofSize[A](shape: Int*): FloatTensor[A] = newTensor(shape)

  def newVector[A](size: Int): FloatTensor[A] = {
    val storage = Storage.ofSize[Float](size)
    val tensor = new FloatTensor[A](THFloatTensor_newWithStorage1d(storage.ptr, 0, size, 1))
    tensor
  }

  def newTensor[A](shape: Seq[Int]): FloatTensor[A] = {
    val sh = shape.map(_.toLong).toArray
    val nShape = NativeArray.fromJvm(sh :+ 0l)                                // Torch requires a 0-ending array here
    val nStride = NativeArray.fromJvm(ShapeUtils.contiguousStrides(sh) :+ 0l) // Torch requires a 0-ending array here

    val tensor = newVector(ShapeUtils.product(shape).toInt)
    THFloatTensor_resizeNd(tensor.ptr, shape.length, nShape.ptr, nStride.ptr)
    THFloatTensor_zero(tensor.ptr)  // initialize with 0
    tensor.asInstanceOf[FloatTensor[A]]
  }

  def fromFlatArray[A](array: Array[Float], shape: Seq[Int]) = {
    val tensor = newTensor[A](shape)
    val storage = tensor.storage
    var i = 0
    while (i < array.length) {
      storage(i) = array(i)
      i += 1
    }
    tensor
  }

  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]) = ???

  def inv[A](x: FloatTensor[A]) = invImpl(x)
  private def invImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def sqrt[A](x: FloatTensor[A]) = sqrtImpl(x)
  private def sqrtImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def log[A](x: FloatTensor[A]) = logImpl(x)
  private def logImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def exp[A](x: FloatTensor[A]) = expImpl(x)
  private def expImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def log1p[A](x: FloatTensor[A]) = log1pImpl(x)
  private def log1pImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def expm1[A](x: FloatTensor[A]) = expm1Impl(x)
  private def expm1Impl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def sin[A](x: FloatTensor[A]) = sinImpl(x)
  private def sinImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def cos[A](x: FloatTensor[A]) = cosImpl(x)
  private def cosImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def tan[A](x: FloatTensor[A]) = tanImpl(x)
  private def tanImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def arcsin[A](x: FloatTensor[A]) = arcsinImpl(x)
  private def arcsinImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def arccos[A](x: FloatTensor[A]) = arccosImpl(x)
  private def arccosImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def arctan[A](x: FloatTensor[A]) = arctanImpl(x)
  private def arctanImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def sigmoid[A](x: FloatTensor[A]) = sigmoidImpl(x)
  private def sigmoidImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def relu[A](x: FloatTensor[A]) = ???

  def abs[A](x: FloatTensor[A]) = absImpl(x)
  private def absImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def sgn[A](x: FloatTensor[A]) = ???

  def pos[A](x: FloatTensor[A]) = ???

  def zeroBy[A](x: FloatTensor[A]) = zeroByImpl(x)
  private def zeroByImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def add[A](x: FloatTensor[A], y: FloatTensor[A]) = addImpl(x, y)
  private def addImpl[A](x: FloatTensor[A], y: FloatTensor[A]): FloatTensor[A] = macro addSub[A]

  def addInplace[A](x: FloatTensor[A], y: FloatTensor[A]) = {
    THFloatTensor_add(x.ptr, y.ptr, 1f)
    x
  }

  def addScalar[A](x: FloatTensor[A], u: Float) = addScalarImpl(x, u)
  private def addScalarImpl[A](x: FloatTensor[A], u: Float): FloatTensor[A] = macro addSubScalar[A]

  def neg[A](x: FloatTensor[A]) = negImpl(x)
  private def negImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def sub[A](x: FloatTensor[A], y: FloatTensor[A]) = subImpl(x, y)
  private def subImpl[A](x: FloatTensor[A], y: FloatTensor[A]): FloatTensor[A] = macro addSub[A]

  def subScalar[A](x: FloatTensor[A], u: Float) = subScalarImpl(x, u)
  private def subScalarImpl[A](x: FloatTensor[A], u: Float): FloatTensor[A] = macro addSubScalar[A]

  def mul[A](x: FloatTensor[A], y: FloatTensor[A]) = mulImpl(x, y)
  private def mulImpl[A](x: FloatTensor[A], y: FloatTensor[A]): FloatTensor[A] = macro elementwise2[A]

  def div[A](x: FloatTensor[A], y: FloatTensor[A]) = divImpl(x, y)
  private def divImpl[A](x: FloatTensor[A], y: FloatTensor[A]): FloatTensor[A] = macro elementwise2[A]

  def scale[A](x: FloatTensor[A], u: Float) = scaleImpl(x, u)
  private def scaleImpl[A](x: FloatTensor[A], u: Float): FloatTensor[A] = macro addSubScalar[A]

  def sqr[A](x: FloatTensor[A]) = mul(x, x)

  def sum[A](x: FloatTensor[A]) = THFloatTensor_sumall(x.ptr).toFloat
  def product[A](x: FloatTensor[A]) = THFloatTensor_prodall(x.ptr).toFloat

  def dot[A](x: FloatTensor[A], y: FloatTensor[A]) = THFloatTensor_dot(x.ptr, y.ptr).toFloat

  def matMul[U, V, W](x: FloatTensor[(U, V)], y: FloatTensor[(V, W)]) = {
    val z = newTensor[(U, W)](List(sizeOfDim(x, 0), sizeOfDim(y, 1)))
    THFloatTensor_addmm(z.ptr, 0f, z.ptr, 1f, x.ptr, y.ptr)
    z
  }

  def mvMul[U, V](x: FloatTensor[(U, V)], y: FloatTensor[V]) = {
    val z = newVector[U](sizeOfDim(x, 0))
    THFloatTensor_addmv(z.ptr, 0f, z.ptr, 1f, x.ptr, y.ptr)
    z
  }

  def vvMul[U, V](x: FloatTensor[U], y: FloatTensor[V]) = {
    val z = newTensor[(U, V)](List(sizeOfDim(x, 0), sizeOfDim(y, 0)))
    THFloatTensor_addr(z.ptr, 0f, z.ptr, 1f, x.ptr, y.ptr)
    z
  }

  def contract[U, V, W](x: FloatTensor[U], y: FloatTensor[V])(implicit sd: SymDiff.Aux[U, V, W]) = ???

  def rank[A](x: FloatTensor[A]) = THFloatTensor_nDimension(x.ptr)

  def sizeOfDim[A](x: FloatTensor[A], dim: Int) = THFloatTensor_size(x.ptr, dim).toInt

  override def numElements[A](x: FloatTensor[A]) = THFloatTensor_nElement(x.ptr).toInt
  def get[A](x: FloatTensor[A], is: Seq[Int]) = ???
  def set[A](x: FloatTensor[A], is: Seq[Int], v: Float): Unit = ???

  def wrapScalar(x: Float) = ???
  def unwrapScalar(x: FloatTensor[Unit]) = ???

  def transpose[U, V](x: FloatTensor[(U, V)]) = {
    val y = new FloatTensor[(V, U)](THFloatTensor_new())
    THFloatTensor_transpose(y.ptr, x.ptr, 0, 1)
    y
  }

  def sliceAlong[U, X, V](x: FloatTensor[U], axis: X, n: Int)(implicit rx: Remove.Aux[U, X, V]) = ???
  def unstackAlong[U, X, V](x: FloatTensor[U], axis: X)(implicit rx: Remove.Aux[U, X, V]) = ???
  def expandDim[U, I <: Nat, X <: Dim, V](x: FloatTensor[U])(implicit ix: InsertAt.Aux[U, I, X, V]) = ???
  def renameAxis[U, V](x: FloatTensor[U]) = ???

  def map[A](x: FloatTensor[A])(f: Float => Float) = ???
  def map2[A](x1: FloatTensor[A], x2: FloatTensor[A])(f: (Float, Float) => Float) = ???
  def map3[A](x1: FloatTensor[A], x2: FloatTensor[A], x3: FloatTensor[A])(f: (Float, Float, Float) => Float) = ???
}