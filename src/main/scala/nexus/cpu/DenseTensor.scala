package nexus.cpu

import nexus._
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
trait DenseTensor[D, A <: HList]
    extends UntypedDenseTensor[D]
    with TensorLike[D, A, DenseTensor[D, A]] { self =>

  val axes: A
  val handle: Array[D]
  val stride: Array[Int]
  val offset: Int
  val shape: Array[Int]

  //def update(indices: Int*)(newValue: D) = handle(index(indices)) = newValue

  protected def slice0[N <: Nat, T <: HList]
  (axis: N, i: Int)
  (implicit t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): DenseTensor[D, T] =
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
  (implicit d: InsertAt.Aux[A, N, X, T], nn: ToInt[N]) =


  def asSeq: Seq[D] = ???

  override def stringPrefix = "CPUTensor"

}

object DenseTensor {

  def scalar[D: ClassTag](value: D): DenseTensor[D, HNil] =
    new Contiguous(Array(value), HNil, Array())

  def fill[D: ClassTag, A <: HList](value: => D, axes: A, shape: Array[Int]): DenseTensor[D, A] =
    new Contiguous(Array.fill(ShapeUtils.product(shape))(value), axes, shape)

  def fromFlatArray[D, A <: HList](array: Array[D], axes: A, shape: Array[Int]): DenseTensor[D, A] =
    new Contiguous(array, axes, shape)

  def fromNestedArray[D, A <: HList, N <: Nat, T]
  (axes: A)(array: T)
  (implicit nest: Nest[T, D, N], nn: Length.Aux[A, N]) =
    fromFlatArray(nest.flatten(array), axes, nest.shape(array))

  class Contiguous[D, A <: HList](handle: Array[D], val axes: A, shape: Array[Int])
    extends UntypedDenseTensor.Contiguous[D](handle, shape) with DenseTensor[D, A]

  class View[D, A <: HList](handle: Array[D], val axes: A, shape: Array[Int], offset: Int, stride: Array[Int])
    extends UntypedDenseTensor.View[D](handle, shape, offset, stride) with DenseTensor[D, A]


}
