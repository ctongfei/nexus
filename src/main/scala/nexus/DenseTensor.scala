package nexus

import nexus.shape._
import nexus.storage._
import shapeless._
import shapeless.nat._
import shapeless.ops._
import shapeless.ops.hlist._
import shapeless.ops.nat._

/**
 * @author Tongfei Chen
 */
class DenseTensor[D <: DType, S <: HList](

  val storage: Storage[D],
  val offset: Int,
  val shape: IndexedSeq[Int],
  val stride: IndexedSeq[Int]

)
  extends Tensor[D, S]
{

  final def isDense = true

  def size = {
    var s = 1
    var k = 0
    while (k < shape.length) {
      s *= shape(k)
      k += 1
    }
    s
  }

  // Column major
  def index(indices: Seq[Int]) = {
    var i = 0
    var k = 0
    while (k < shape.length) {
      i += indices(k) * stride(k)
      k += 1
    }
    i
  }

  def apply[@specialized R](indices: Int*)(implicit ev: DType.Ev[R, D]): R = storage(index(indices))

  def update[@specialized R](indices: Int*)(newValue: R)(implicit ev: DType.Ev[R, D]): Unit = storage(index(indices)) = newValue

  protected def slice0[N <: Nat, T <: HList]
  (axis: N, i: Int)
  (implicit t: RemoveAt.Aux[N, S, T], axisN: ToInt[N]): DenseTensor[D, T] =
    new DenseTensor[D, T](
      storage = storage,
      offset = stride(axisN()) * i,
      shape = Shape.removeAt(shape, axisN()),
      stride = Shape.removeAt(stride, axisN())
    )

  def slice[H, T <: HList]
    (i: Int)
    (implicit t: RemoveAt.Aux[_0, S, T]): DenseTensor[D, T] =
  {
    slice0(_0, i)
  }

  def sliceAlong[A, N <: Nat, T <: HList]
  (axis: A, i: Int)
  (implicit n: IndexOf.Aux[A, S, N], t: RemoveAt.Aux[N, S, T], nn: ToInt[N]): DenseTensor[D, T] =
  {
    slice0(n(), i)
  }

  def along[A, N <: Nat, T <: HList]
  (axis: A)
  (implicit n: IndexOf.Aux[A, S, N], t: RemoveAt.Aux[N, S, T], nn: ToInt[N]): IndexedSeq[DenseTensor[D, T]] =
  {
    (0 until shape(nn())) map { i => slice0(n(), i) }
  }

  override def toString = {
    val header = s"DenseTensor[Rank=${shape.length}, Axes=${}]"
  }

}

object DenseTensor {

  def ofStorage[D <: DType, S <: HList](storage: Storage[D], axes: S, shape: IndexedSeq[Int]) =
    new DenseTensor[D, S](storage, 0, shape, shape.scanRight(1)(_*_).tail)


}