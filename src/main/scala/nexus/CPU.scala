package nexus
import nexus.shape._
import shapeless._
import shapeless.nat._
import shapeless.ops.hlist._
import shapeless.ops.nat._

/**
 * Represents the CPU device.
 * @author Tongfei Chen
 */
object CPU extends CPU

sealed class CPU extends DeviceLike[CPU] {

  def name: String = "CPU"

  //region SUPPORTED DATA TYPES ON THIS DEVICE IS REGISTERED HERE

  implicit object float32 extends Store[Float32, Float, Array[Float]] {
    def allocate(n: Int) = new Array[Float](n)
    def fromArray(array: Array[Float]) = array
    def toArray(handle: Array[Float]) = handle
    def get(handle: Array[Float], i: Int) = handle(i)
    def set(handle: Array[Float], i: Int, x: Float) = handle(i) = x
  }

  implicit object float64 extends Store[Float64, Double, Array[Double]] {
    def allocate(n: Int) = new Array[Double](n)
    def fromArray(array: Array[Double]) = array
    def toArray(handle: Array[Double]) = handle
    def get(handle: Array[Double], i: Int) = handle(i)
    def set(handle: Array[Double], i: Int, x: Double) = handle(i) = x
  }

  implicit object int32 extends Store[Int32, Int, Array[Int]] {
    def allocate(n: Int) = new Array[Int](n)
    def fromArray(array: Array[Int]) = array
    def toArray(handle: Array[Int]) = handle
    def get(handle: Array[Int], i: Int) = handle(i)
    def set(handle: Array[Int], i: Int, x: Int) = handle(i) = x
  }

  implicit object int64 extends Store[Int64, Long, Array[Long]] {
    def allocate(n: Int) = new Array[Long](n)
    def fromArray(array: Array[Long]) = array
    def toArray(handle: Array[Long]) = handle
    def get(handle: Array[Long], i: Int) = handle(i)
    def set(handle: Array[Long], i: Int, x: Long) = handle(x) = i
  }

  //endregion

  abstract class Tensor[D <: DType, A <: HList] extends TensorLike[Tensor[D, A]] { self =>

    type Self = Tensor[D, A]
    type Axes = A
    type Elem = D
    type Device = CPU

    val device: Device = CPU

    val dtype: Elem
    val axes: Axes
    val handle: Handle
    val offset: Int
    val shape: Seq[Int]
    val stride: Seq[Int]
    implicit val bridge: Store[Elem, JvmElem, Handle]

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

    def apply(indices: Int*) = bridge.get(handle, index(indices))
    def update(indices: Int*)(newValue: JvmElem) = bridge.set(handle, index(indices), newValue)

    protected def slice0[N <: Nat, T <: HList]
    (axis: N, i: Int)
    (implicit t: RemoveAt.Aux[A, N, T], axisN: ToInt[N]): Tensor[D, T] =
      new Tensor[D, T] {
        val dtype = self.dtype
        val handle = self.handle
        val axes = t(self.axes)
        val offset = self.stride(axisN()) * i
        val shape = Shape.removeAt(self.shape, axisN())
        val stride = Shape.removeAt(self.stride, axisN())
        val bridge = self.bridge
        type JvmElem = self.JvmElem
        type Storage = self.Handle
      }

    def slice[H, T <: HList]
    (i: Int)
    (implicit t: RemoveAt.Aux[A, _0, T]): Tensor[D, T] = slice0(_0, i)

    def sliceAlong[X, N <: Nat, T <: HList]
    (axis: X, i: Int)
    (implicit n: IndexOf.Aux[A, X, N], t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): Tensor[D, T] = slice0(n(), i)

    def along[X, N <: Nat, T <: HList]
    (axis: X)
    (implicit n: IndexOf.Aux[A, X, N], t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): IndexedSeq[Tensor[D, T]] =
      (0 until shape(nn())) map { i => slice0(n(), i) }

    def unary_- = ???
    def unary_+ = ???
    def +(that: Tensor[D, A]) = ???
    def -(that: Tensor[D, A]) = ???
    def |*|(that: Tensor[D, A]) = ???
    def |/|(that: Tensor[D, A]) = ???
    def :*(that: JvmElem) = ???

    def |>[Y](op: Op1[Self, Y]) = op.forward(self)
  }

  object Tensor {

    private def fromHandle[D <: DType, A <: HList, R, H]
    (_dtype: D, _handle: H, _axes: A, _shape: Seq[Int])
    (implicit _support: Store[D, R, H]): Tensor[D, A] = new Tensor[D, A] {
      val dtype = _dtype
      val axes = _axes
      val handle = _handle
      val offset = 0
      val shape = _shape
      val stride = _shape.scanRight(1)(_*_).tail
      val bridge = _support
      type Handle = H
      type JvmElem = R
    }


    def fromNestedArray[D <: DType, A <: HList, N <: Nat, R, H, T]
    (dtype: D, axes: A)(array: T)
    (implicit support: Store[D, R, H], nest: Nest[T, R, N], nn: Length.Aux[A, N]) = {
      val shape = nest.shape(array)
      fromHandle(dtype, support.fromArray(nest.flatten(array)), axes, shape)
    }

    def fromFlatArray[D <: DType, A <: HList, R, S](dtype: D, axes: A, shape: Array[Int])(array: Array[R])(implicit support: Store[D, R, S]) = {
      fromHandle(dtype, support.fromArray(array), axes, shape)
    }

  }


}
