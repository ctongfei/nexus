package nexus.torch

import nexus.tensor._
import nexus.tensor.instances._
import nexus.torch.jni.THJNI._
import shapeless.Nat

import scala.reflect._


object FloatTensorOps extends IsRealTensorK[({type L[A] = Tensor[Float, A]})#L, Float] {

  val R = Float32
  implicit val elementTypeClassTag: ClassTag[Float] = ClassTag.Float
  private type T[A] = Tensor[Float, A]

  private def newT[A] = new T[A](THFloatTensor_new())

  def zeroBy[A](x: T[A]) = {
    val y = newT[A]
    THFloatTensor_zerosLike(x.ptr, y.ptr)
    y
  }

  def add[A](x: T[A], y: T[A]) = {
    val z = newT[A]
    THFloatTensor_cadd(x.ptr, y.ptr, 1f, z.ptr)
    z
  }

  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]) = ???
  def addI[A](x1: Tensor[Float, A], x2: Tensor[Float, A]) = ???
  def addS[A](x: Tensor[Float, A], u: Float) = ???
  def neg[A](x: Tensor[Float, A]) = ???
  def sub[A](x1: Tensor[Float, A], x2: Tensor[Float, A]) = ???
  def subS[A](x: Tensor[Float, A], u: Float) = ???
  def eMul[A](x1: Tensor[Float, A], x2: Tensor[Float, A]) = ???
  def eDiv[A](x1: Tensor[Float, A], x2: Tensor[Float, A]) = ???
  def scale[A](x: Tensor[Float, A], u: Float) = ???
  def eInv[A](x: Tensor[Float, A]) = ???
  def eSqr[A](x: Tensor[Float, A]) = ???
  def eSqrt[A](x: Tensor[Float, A]) = ???
  def transpose[A, B](x: Tensor[Float, (A, B)]) = ???
  def eLog[A](x: Tensor[Float, A]) = ???
  def eExp[A](x: Tensor[Float, A]) = ???
  def eLog1p[A](x: Tensor[Float, A]) = ???
  def eExpm1[A](x: Tensor[Float, A]) = ???
  def eSin[A](x: Tensor[Float, A]) = ???
  def eCos[A](x: Tensor[Float, A]) = ???
  def eTan[A](x: Tensor[Float, A]) = ???
  def sigmoid[A](x: Tensor[Float, A]) = ???
  def relu[A](x: Tensor[Float, A]) = ???
  def eAbs[A](x: Tensor[Float, A]) = ???
  def eSgn[A](x: Tensor[Float, A]) = ???
  def pos[A](x: Tensor[Float, A]) = ???
  def sum(x: Tensor[Float, _]) = ???
  def mmMul[A, B, C](x: Tensor[Float, (A, B)], y: Tensor[Float, (B, C)]) = ???
  def mvMul[A, B](x: Tensor[Float, (A, B)], y: Tensor[Float, B]) = ???
  def vvMul[A, B](x: Tensor[Float, A], y: Tensor[Float, B]) = ???
  def dot[A](x: Tensor[Float, A], y: Tensor[Float, A]) = ???
  def contract[A, B, C](x: Tensor[Float, A], y: Tensor[Float, B])(implicit sd: SymDiff.Aux[A, B, C]) = ???
  def rank(x: Tensor[Float, _]) = ???
  def shape(x: Tensor[Float, _]) = ???
  def size(x: Tensor[Float, _]) = ???
  def get(x: Tensor[Float, _], is: Array[Int]) = ???
  def set(x: Tensor[Float, _], is: Array[Int], v: Float): Unit = ???
  def fromFlatArray[A](array: Array[Float], shape: Array[Int]) = ???
  def wrapScalar(x: Float) = ???
  def unwrapScalar(x: Tensor[Float, Unit]) = ???
  def map[A](x: Tensor[Float, A])(f: Float => Float) = ???
  def map2[A](x1: Tensor[Float, A], x2: Tensor[Float, A])(f: (Float, Float) => Float) = ???
  def map3[A](x1: Tensor[Float, A], x2: Tensor[Float, A], x3: Tensor[Float, A])(f: (Float, Float, Float) => Float) = ???
  def expandDim[A, I <: Nat, X <: Dim, B](x: Tensor[Float, A])(implicit ix: InsertAt.Aux[A, I, X, B]) = ???
  def newTensor[A](shape: Array[Int]) = ???
  def elementClassTag = ???
  def get(x: Tensor[Float, _], is: Seq[Int]) = ???
  def set(x: Tensor[Float, _], is: Seq[Int], v: Float): Unit = ???
  def newTensor[A](shape: Seq[Int]) = ???
  def fromFlatArray[A](array: Array[Float], shape: Seq[Int]) = ???
  def renameAxis[A, B](x: Tensor[Float, A]) = ???
  def sliceAlong[A, U, B](x: Tensor[Float, A], axis: U, n: Int)(implicit rx: Aux[A, U, B]) = ???
  def unstackAlong[A, U, B](x: Tensor[Float, A], axis: U)(implicit rx: Aux[A, U, B]) = ???
}
