package nexus

import nexus.shape._
import shapeless._
import shapeless.nat._
import shapeless.ops.nat._

/**
 * @author Tongfei Chen
 */
trait DenseTensor[D <: DType, A <: HList] extends Tensor[D, A] {

  val axes: A
  val device: Device
  val storage: Storage
  val offset: Int
  val shape: Seq[Int]
  val stride: Seq[Int]
  implicit val support: DTypeSupport[D, R, Storage]

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

  def apply(indices: Int*) = support.get(storage, index(indices))
  def update(indices: Int*)(newValue: R) = support.set(storage, index(indices), newValue)

  protected def slice0[N <: Nat, T <: HList]
  (axis: N, i: Int)
  (implicit t: RemoveAt.Aux[N, A, T], axisN: ToInt[N]): DenseTensor[D, T] =
    device.view[D, T, R, Storage](
      storage = storage,
      axes = t(axes),
      offset = stride(axisN()) * i,
      shape = Shape.removeAt(shape, axisN()),
      stride = Shape.removeAt(stride, axisN())
    )

  def slice[H, T <: HList]
    (i: Int)
    (implicit t: RemoveAt.Aux[_0, A, T]): DenseTensor[D, T] = slice0(_0, i)

  def sliceAlong[X, N <: Nat, T <: HList]
  (axis: X, i: Int)
  (implicit n: IndexOf.Aux[X, A, N], t: RemoveAt.Aux[N, A, T], nn: ToInt[N]): DenseTensor[D, T] = slice0(n(), i)

  def along[X, N <: Nat, T <: HList]
  (axis: X)
  (implicit n: IndexOf.Aux[X, A, N], t: RemoveAt.Aux[N, A, T], nn: ToInt[N]): IndexedSeq[DenseTensor[D, T]] =
    (0 until shape(nn())) map { i => slice0(n(), i) }

  override def toString = {
    val axesRawString = axes.toString
    val axesString = axesRawString.substring(0, axesRawString.lastIndexOf(" :: "))
    val header = s"Tensor[Rank=${shape.length}, Axes=$axesString]"
    header
  }

}
