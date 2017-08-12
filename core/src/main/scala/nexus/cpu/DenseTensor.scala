package nexus.cpu

import nexus._
import nexus.typelevel._
import nexus.util._
import shapeless._
import shapeless.ops.hlist._
import shapeless.ops.nat._

import scala.reflect._

/**
 * A tensor stored in main memory, and whose operations run on the CPU.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait DenseTensor[A <: HList]
    extends UntypedDenseTensor
    with TensorLike[Float, A, DenseTensor[A]] { self =>

  val axes: A
  val handle: Array[Float]
  val stride: Array[Int]
  val offset: Int
  val shape: Array[Int]

  //def update(indices: Int*)(newValue: Float) = handle(index(indices)) = newValue

  protected def slice0[N <: Nat, T <: HList]
  (axis: N, i: Int)
  (implicit t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): DenseTensor[T] =
    sliceUntyped(nn(), i) typeWith t(axes)

  def sliceAlong[X, N <: Nat, T <: HList]
  (axis: X, i: Int)
  (implicit n: IndexOf.Aux[A, X, N], t: RemoveAt.Aux[A, N, T], nn: ToInt[N]) = slice0(n(), i)

  def along[X, N <: Nat, T <: HList]
  (axis: X)
  (implicit n: IndexOf.Aux[A, X, N], t: RemoveAt.Aux[A, N, T], nn: ToInt[N]) =
    (0 until shape(nn())) map { i => slice0(n(), i) }

  def expandDim[X, N <: Nat, T <: HList]
  (axis: X, n: N)
  (implicit d: InsertAt.Aux[A, N, X, T], nn: ToInt[N]) = ???


  def asSeq: Seq[Float] = ???

  override def stringPrefix = "CPUTensor"

}



object DenseTensor {

  def scalar(value: Float): DenseTensor[HNil] =
    new Contiguous(Array(value), HNil, Array())

  def fill[A <: HList](value: => Float, axes: A, shape: Array[Int]): DenseTensor[A] =
    new Contiguous(Array.fill(ShapeUtils.product(shape))(value), axes, shape)

  def fromFlatArray[A <: HList](array: Array[Float], axes: A, shape: Array[Int]): DenseTensor[A] =
    new Contiguous(array, axes, shape)

  def fromNestedArray[A <: HList, N <: Nat, T]
  (axes: A)(array: T)
  (implicit nest: Nest[T, Float, N], nn: Length.Aux[A, N]) =
    fromFlatArray(nest.flatten(array), axes, nest.shape(array))

  class Contiguous[A <: HList](handle: Array[Float], val axes: A, shape: Array[Int])
    extends UntypedDenseTensor.Contiguous(handle, shape) with DenseTensor[A]

  class View[A <: HList](handle: Array[Float], val axes: A, shape: Array[Int], offset: Int, stride: Array[Int])
    extends UntypedDenseTensor.View(handle, shape, offset, stride) with DenseTensor[A]

}

object Scalar {
  def apply(x: Float) = DenseTensor.fromFlatArray(Array(x), $, Array())
}

object Vector {
  def apply[A](A: A)(array: Array[Float]) = DenseTensor.fromFlatArray(array, A::$, Array(array.length))
}
