package nexus.cpu

import nexus._
import nexus.shape._
import shapeless._
import shapeless.ops.hlist._
import shapeless.ops.nat._

import scala.collection.mutable

/**
 * A tensor stored in main memory, and whose operations run on the CPU.
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class Tensor[D, A <: HList] extends TensorLike[D, A, Tensor[D, A]] { self =>

  val axes: A
  val handle: Array[D]
  val stride: Array[Int]
  val offset: Int
  val shape: Array[Int]

  def index(indices: Seq[Int]) = {
    var i = offset
    var k = 0
    while (k < rank) {
      i += indices(k) * stride(k)
      k += 1
    }
    i
  }

  def apply(indices: Int*) = handle(index(indices))
  //def update(indices: Int*)(newValue: D) = handle(index(indices)) = newValue

  protected def slice0[N <: Nat, T <: HList]
  (axis: N, i: Int)
  (implicit t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): Tensor[D, T] = new Tensor[D, T] {
    val axes = t(self.axes)
    val handle = self.handle
    val stride = ShapeUtils.removeAt(self.stride, nn())
    val offset = self.stride(nn()) * i
    val shape = ShapeUtils.removeAt(self.shape, nn())
  }

  def sliceAlong[X, N <: Nat, T <: HList]
  (axis: X, i: Int)
  (implicit n: IndexOf.Aux[A, X, N], t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): Tensor[D, T] =
    slice0(n(), i)

  def along[X, N <: Nat, T <: HList]
  (axis: X)
  (implicit n: IndexOf.Aux[A, X, N], t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): IndexedSeq[Tensor[D, T]] =
    (0 until shape(nn())) map { i => slice0(n(), i) }

  override def stringPrefix = "CPUTensor"

  def stringBody = {
    val sb = new StringBuilder
    val indices = Array.fill(self.rank)(0)
    var d = 0
    while
  }
}

object Tensor {

  def fromArray[D, A <: HList](array: Array[D], _axes: A, _shape: Array[Int]): Tensor[D, A] = new Tensor[D, A] {
    val axes = _axes
    val handle = array
    val stride = _shape.scanRight(1)(_ * _).tail
    val offset = 0
    val shape = _shape
  }

  def fromNestedArray[D, A <: HList, N <: Nat, T]
  (axes: A)(array: T)
  (implicit nest: Nest[T, D, N], nn: Length.Aux[A, N]) =
    fromArray(nest.flatten(array), axes, nest.shape(array))

}
