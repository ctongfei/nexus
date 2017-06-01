package nexus
import shapeless.HList

/**
 * @author Tongfei Chen
 */
object CPU extends Device {

  def name: String = "CPU"

  implicit object float32 extends DTypeSupport[Float32, Float, MemoryFloat32Storage] {
    def newStorage(n: Int) = new MemoryFloat32Storage(new Array[Float](n))
    def get(storage: MemoryFloat32Storage, i: Int) = storage.data(i)
    def set(storage: MemoryFloat32Storage, i: Int, x: Float) = storage.data(i) = x
  }

  implicit object float64 extends DTypeSupport[Float64, Double, MemoryFloat64Storage] {
    def newStorage(n: Int) = new MemoryFloat64Storage(new Array[Double](n))
    def get(storage: MemoryFloat64Storage, i: Int) = storage.data(i)
    def set(storage: MemoryFloat64Storage, i: Int, x: Double) = storage.data(i) = x
  }


  def tensorFromStorage[D <: DType, A <: HList, _R, _S](_storage: _S, _axes: A, _shape: Seq[Int])(implicit _support: DTypeSupport[D, _R, _S]): DenseTensor[D, A] = new DenseTensor[D, A] {
    val device = CPU
    val axes = _axes
    val storage = _storage
    val offset = 0
    val shape = _shape
    val stride = _shape.scanRight(1)(_ * _).tail
    val support = _support
    type Storage = _S
    type R = _R
  }

  def view[D <: DType, A <: HList, _R, _S](_storage: _S, _axes: A, _offset: Int, _shape: Seq[Int], _stride: Seq[Int])(implicit _support: DTypeSupport[D, _R, _S]): DenseTensor[D, A] = new DenseTensor[D, A] {
    val device = CPU
    val axes = _axes
    val storage = _storage
    val offset = _offset
    val shape = _shape
    val stride = _stride
    val support = _support
    type Storage = _S
    type R = _R
  }

  class MemoryInt32Storage (val data: Array[Int]) extends Storage[Int32]
  class MemoryInt64Storage (val data: Array[Long]) extends Storage[Int64]
  class MemoryFloat32Storage (val data: Array[Float]) extends Storage[Float32]
  class MemoryFloat64Storage (val data: Array[Double]) extends Storage[Float64]

  object Tensor {

    def fromStorage[D <: DType, A <: HList, R, S](storage: S, axes: A, shape: Seq[Int])(implicit support: DTypeSupport[D, R, S]) = tensorFromStorage[D, A, R, S](storage, axes, shape)

  }

  def add[D <: DType, A <: HList](a: Tensor[D, A], b: Tensor[D, A]): Tensor[D, A] = ???
  def sub[D <: DType, A <: HList](a: Tensor[D, A], b: Tensor[D, A]): Tensor[D, A] = ???
  def mul[D <: DType, A <: HList](a: Tensor[D, A], b: Tensor[D, A]): Tensor[D, A] = ???
  def div[D <: DType, A <: HList](a: Tensor[D, A], b: Tensor[D, A]): Tensor[D, A] = ???
  def neg[D <: DType, A <: HList](a: Tensor[D, A]): Tensor[D, A] = ???
}
