package nexus.cpu

import nexus._
import nexus.util._
import shapeless._

trait UntypedDenseTensor[D] extends UntypedTensorLike[D, UntypedDenseTensor[D]] { self =>
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

  def sliceUntyped(n: Int, i: Int): UntypedDenseTensor[D] =
    new UntypedDenseTensor.Sliced(
      handle = self.handle,
      shape = ShapeUtils.removeAt(self.shape, n),
      offset = self.stride(n) * i,
      stride = ShapeUtils.removeAt(self.stride, n)
    )

  def typeWith[A <: HList](axes: A): DenseTensor[D, A]

  def stringBody: String = rank match {
    case 0 =>
      handle(offset).toString
    case 1 =>
      (0 until shape(0)).map { i => handle(offset + i * stride(0)) }.mkString("[", ", \t", "]")
    case _ =>
      (0 until shape(0)).map { i => sliceUntyped(0, i).stringBody }.mkString("[", "\n", "]")
  }

}

object UntypedDenseTensor {

  class Contiguous[D](val handle: Array[D], val shape: Array[Int]) extends UntypedDenseTensor[D] { self =>
    val stride = shape.scanRight(1)(_ * _).tail
    val offset = 0
    def typeWith[A <: HList](axes: A) = new DenseTensor.Contiguous[D, A](handle, axes, shape)
  }

  class Sliced[D](val handle: Array[D], val shape: Array[Int], val offset: Int, val stride: Array[Int]) extends UntypedDenseTensor[D] {
    def typeWith[A <: HList](axes: A) = new DenseTensor.Sliced[D, A](handle, axes, shape, offset, stride)
  }


}
