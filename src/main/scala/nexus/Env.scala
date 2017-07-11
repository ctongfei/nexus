package nexus

import nexus.typelevel._
import nexus.util._
import shapeless._

import scala.annotation._

/**
 * Runtime environment of a tensor of type T[D, _] and data type D.
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot find a device to run ${T} with element type ${D}.")
trait Env[T[_, _ <: HList], D] {

  type Handle

  type Scalar = T[D, $]
  type Vector[A] = T[D, A::$]
  type Matrix[A, B] = T[D, A::B::$]
  type Tensor[A <: HList] = T[D, A]

  def newTensor[A <: HList](axes: A, shape: Array[Int]): Tensor[A]
  def newGaussianTensor[A <: HList](μ: Double, σ2: Double, axes: A, shape: Array[Int]): Tensor[A]

  def untype(x: T[D, _]): Handle
  def typeOf[A <: HList](x: Tensor[A]): A
  def typeWith[A <: HList](x: Handle, a: A): Tensor[A]

  def zero: D
  def one: D

  def fromDouble(d: Double): D
  def fromFloat(f: Float): D

  def getScalar(x: Handle): D
  def scalar(x: D): Handle

  def addInplace(x: Handle, d: Handle): Unit

  def addScalarU(x: Handle, u: D): Handle
  def addScalar[A <: HList](x: Tensor[A], u: D): Tensor[A] = typeWith(addScalarU(untype(x), u), typeOf(x))

  def negS(x: D): D

  def negU(x: Handle): Handle
  def neg[A <: HList](x: Tensor[A]): Tensor[A] = typeWith(negU(untype(x)), typeOf(x))

  def addU(x: Handle, y: Handle): Handle
  def add[A <: HList](x: Tensor[A], y: Tensor[A]): Tensor[A] = typeWith(addU(untype(x), untype(y)), typeOf(x))

  def subU(x: Handle, y: Handle): Handle
  def sub[A <: HList](x: Tensor[A], y: Tensor[A]): Tensor[A] = typeWith(subU(untype(x), untype(y)), typeOf(x))

  def mulU(x: Handle, y: Handle): Handle
  def mul[A <: HList](x: Tensor[A], y: Tensor[A]): Tensor[A] = typeWith(mulU(untype(x), untype(y)), typeOf(x))

  def divU(x: Handle, y: Handle): Handle
  def div[A <: HList](x: Tensor[A], y: Tensor[A]): Tensor[A] = typeWith(divU(untype(x), untype(y)), typeOf(x))

  def scaleU(x: Handle, u: D): Handle
  def scale[A <: HList](x: Tensor[A], u: D): Tensor[A] = typeWith(scaleU(untype(x), u), typeOf(x))

  def invU(x: Handle): Handle
  def inv[A <: HList](x: Tensor[A]): Tensor[A] = typeWith(invU(untype(x)), typeOf(x))

  def sqrU(x: Handle): Handle
  def sqr[A <: HList](x: Tensor[A]): Tensor[A] = typeWith(sqrU(untype(x)), typeOf(x))

  def transposeU(x: Handle): Handle
  def transpose[A, B](x: Matrix[A, B]): Matrix[B, A] = typeWith(transposeU(untype(x)), AxesUtils.swap(typeOf(x)))

  def logU(x: Handle): Handle
  def log[A <: HList](x: Tensor[A]): Tensor[A] = typeWith(logU(untype(x)), typeOf(x))

  def expU(x: Handle): Handle
  def exp[A <: HList](x: Tensor[A]): Tensor[A] = typeWith(expU(untype(x)), typeOf(x))

  def sigmoidU(x: Handle): Handle
  def sigmoid[A <: HList](x: Tensor[A]): Tensor[A] = typeWith(sigmoidU(untype(x)), typeOf(x))

  def reluU(x: Handle): Handle
  def relu[A <: HList](x: Tensor[A]): Tensor[A] = typeWith(reluU(untype(x)), typeOf(x))

  def reduceSumU(x: Handle): Handle
  def reduceSum[A <: HList](x: Tensor[A]): Tensor[$] = typeWith(reduceSumU(untype(x)), $)


  def mvMulU(x: Handle, y: Handle): Handle
  def mvMul[A, B](x: Matrix[A, B], y: Vector[B]): Vector[A] = typeWith(mvMulU(untype(x), untype(y)), typeOf(x).head::$)

  def vvMulU(x: Handle, y: Handle): Handle
  def vvMul[A, B](x: Vector[A], y: Vector[B]): Matrix[A, B] = typeWith(vvMulU(untype(x), untype(y)), typeOf(x).head::typeOf(y).head::$)

  def tMulU(x: Handle, y: Handle, matchedIndices: Seq[(Int, Int)]): Handle
  def tMul[A <: HList, B <: HList, C <: HList](x: Tensor[A], y: Tensor[B])(implicit sd: SymDiff.Aux[A, B, C]): Tensor[C] =
    typeWith(tMulU(untype(x), untype(y), sd.matchedIndices), sd(typeOf(x), typeOf(y)))

}
