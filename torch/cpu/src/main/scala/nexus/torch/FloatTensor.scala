package nexus.torch

import nexus.tensor._
import nexus.tensor.typelevel._
import nexus.tensor.util._
import nexus.torch.macros._
import nexus.torch.jni.torchJNI._
import shapeless._

/**
 * Torch-backed CPU float tensor.
 * @tparam A Axis descriptor
 */
class FloatTensor[A] private[torch](ptr: Long) extends Tensor[Float, A](ptr) {
  def storage = new FloatStorage(THFloatTensor_storage(ptr))
}

object FloatTensor extends IsRealTensorK[FloatTensor, Float] {

  import scala.language.experimental.macros

  implicit val R: IsReal[Float] = nexus.tensor.instances.FloatIsReal

  def newTensor[A](shape: Array[Int]) = ???
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

  def sigmoid[A](x: FloatTensor[A]) = ???
  def relu[A](x: FloatTensor[A]) = ???
  def abs[A](x: FloatTensor[A]) = ???
  def sgn[A](x: FloatTensor[A]) = ???
  def pos[A](x: FloatTensor[A]) = ???
  def zeroBy[A](x: FloatTensor[A]) = ???

  def add[A](x: FloatTensor[A], y: FloatTensor[A]) = addImpl(x, y)
  private def addImpl[A](x: FloatTensor[A], y: FloatTensor[A]): FloatTensor[A] = macro elementwise2addSub[A]

  def addInplace[A](x1: FloatTensor[A], x2: FloatTensor[A]) = ???
  def addScalar[A](x: FloatTensor[A], u: Float) = ???

  def neg[A](x: FloatTensor[A]) = negImpl(x)
  private def negImpl[A](x: FloatTensor[A]): FloatTensor[A] = macro elementwise1[A]

  def sub[A](x: FloatTensor[A], y: FloatTensor[A]) = subImpl(x, y)
  private def subImpl[A](x: FloatTensor[A], y: FloatTensor[A]): FloatTensor[A] = macro elementwise2addSub[A]

  def subScalar[A](x: FloatTensor[A], u: Float) = ???

  def mul[A](x: FloatTensor[A], y: FloatTensor[A]) = mulImpl(x, y)
  private def mulImpl[A](x: FloatTensor[A], y: FloatTensor[A]): FloatTensor[A] = macro elementwise2[A]

  def div[A](x: FloatTensor[A], y: FloatTensor[A]) = divImpl(x, y)
  private def divImpl[A](x: FloatTensor[A], y: FloatTensor[A]): FloatTensor[A] = macro elementwise2[A]

  def scale[A](x: FloatTensor[A], u: Float) = ???
  def sqr[A](x: FloatTensor[A]) = ???
  def sum[A](x: FloatTensor[A]) = ???
  def product[A](x: FloatTensor[A]) = ???
  def dot[A](x: FloatTensor[A], y: FloatTensor[A]) = ???
  def matMul[U, V, W](x: FloatTensor[(U, V)], y: FloatTensor[(V, W)]) = ???
  def mvMul[U, V](x: FloatTensor[(U, V)], y: FloatTensor[V]) = ???
  def vvMul[U, V](x: FloatTensor[U], y: FloatTensor[V]) = ???
  def contract[U, V, W](x: FloatTensor[U], y: FloatTensor[V])(implicit sd: SymDiff.Aux[U, V, W]) = ???
  def elementClassTag = ???
  def rank(x: FloatTensor[_]) = ???
  def shape(x: FloatTensor[_]) = ???
  def size(x: FloatTensor[_]) = ???
  def get(x: FloatTensor[_], is: Seq[Int]) = ???
  def set(x: FloatTensor[_], is: Seq[Int], v: Float): Unit = ???
  def newTensor[A](shape: Seq[Int]) = ???

  def fromFlatArray[A](array: Array[Float], shape: Seq[Int]) = {
    require(array.length == ShapeUtils.product(shape))
    val storage = FloatStorage.fromArray(array)
    val tensor = THFloatTensor_new()
    ???
  }

  def wrapScalar(x: Float) = ???
  def unwrapScalar(x: FloatTensor[Unit]) = ???
  def map[A](x: FloatTensor[A])(f: Float => Float) = ???
  def map2[A](x1: FloatTensor[A], x2: FloatTensor[A])(f: (Float, Float) => Float) = ???
  def map3[A](x1: FloatTensor[A], x2: FloatTensor[A], x3: FloatTensor[A])(f: (Float, Float, Float) => Float) = ???
  def transpose[U, V](x: FloatTensor[(U, V)]) = ???
  def sliceAlong[U, X, V](x: FloatTensor[U], axis: X, n: Int)(implicit rx: Remove.Aux[U, X, V]) = ???
  def unstackAlong[U, X, V](x: FloatTensor[U], axis: X)(implicit rx: Remove.Aux[U, X, V]) = ???
  def expandDim[U, I <: Nat, X <: Dim, V](x: FloatTensor[U])(implicit ix: InsertAt.Aux[U, I, X, V]) = ???
  def renameAxis[U, V](x: FloatTensor[U]) = ???
}