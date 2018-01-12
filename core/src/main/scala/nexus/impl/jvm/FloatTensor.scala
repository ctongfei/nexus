package nexus.impl.jvm

import nexus._
import nexus.algebra.typelevel._
import nexus.algebra.typelevel.util._
import shapeless._
import shapeless.ops.hlist._
import shapeless.ops.nat._

import scala.reflect._

/**
 * A tensor stored in main memory, and whose operations run on the CPU.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait FloatTensor[A] extends UntypedFloatTensor { self =>

  val handle: Array[Float]
  val stride: Array[Int]
  val offset: Int
  val shape: Array[Int]

  //def update(indices: Int*)(newValue: Float) = handle(index(indices)) = newValue

  protected def slice0[N <: Nat, T]
  (axis: N, i: Int)
  (implicit t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): FloatTensor[T] =
    sliceUntyped(nn(), i).typeWith[T]


  def sliceAlong[X, N <: Nat, T]
  (axis: X, i: Int)
  (implicit n: IndexOf.Aux[A, X, N], t: RemoveAt.Aux[A, N, T], nn: ToInt[N]) = slice0(n(), i)

  def along[X, N <: Nat, T]
  (axis: X)
  (implicit n: IndexOf.Aux[A, X, N], t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): Seq[FloatTensor[T]] =
    (0 until shape(nn())) map { i => slice0(n(), i) }

  def expandDim[X, N <: Nat, T]
  (axis: X, n: N)
  (implicit d: InsertAt.Aux[A, N, X, T], nn: ToInt[N]) = ???


  def asSeq: Seq[Float] = ???


}



object FloatTensor {

  def scalar(value: Float): FloatTensor[Unit] =
    new Contiguous(Array(value), Array())

  def fill[A](value: => Float, axes: A, shape: Array[Int]): FloatTensor[A] =
    new Contiguous(Array.fill(ShapeUtils.product(shape))(value), shape)

  def fromFlatArray[A](array: Array[Float], shape: Array[Int]): FloatTensor[A] =
    new Contiguous(array, shape)

  def fromNestedArray[A, N <: Nat, T]
  (axes: A)(array: T)
  (implicit nest: Nest[T, Float, N], nn: Len.Aux[A, N]) =
    fromFlatArray[A](nest.flatten(array), nest.shape(array))

  class Contiguous[A](handle: Array[Float], shape: Array[Int])
    extends UntypedFloatTensor.Contiguous(handle, shape) with FloatTensor[A]

  class View[A](handle: Array[Float], shape: Array[Int], offset: Int, stride: Array[Int])
    extends UntypedFloatTensor.View(handle, shape, offset, stride) with FloatTensor[A]

}

object Scalar {
  def apply(x: Float) = FloatTensor.fromFlatArray[Unit](Array(x), Array())
}

object Vector {
  def apply[A](A: A)(array: Array[Float]) = FloatTensor.fromFlatArray[A](array, Array(array.length))
}
