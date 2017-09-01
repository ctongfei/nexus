package nexus.impl.cpu

import nexus._
import nexus.algebra.typelevel.util._
import shapeless._

trait UntypedDenseTensor extends UntypedTensorLike[Float, UntypedDenseTensor] { self =>
  val handle: Array[Float]
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

  def update(indices: Int*)(value: Float) = handle(index(indices)) = value

  def sliceUntyped(n: Int, i: Int): UntypedDenseTensor =
    new UntypedDenseTensor.View(
      handle = self.handle,
      shape = ShapeUtils.removeAt(self.shape, n),
      offset = self.offset + self.stride(n) * i,
      stride = ShapeUtils.removeAt(self.stride, n)
    )

  def typeWith[A <: HList](axes: A): DenseTensor[A]

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

  class Contiguous(val handle: Array[Float], val shape: Array[Int]) extends UntypedDenseTensor { self =>
    val stride = shape.scanRight(1)(_ * _).tail
    val offset = 0
    def typeWith[A <: HList](axes: A) = new DenseTensor.Contiguous[A](handle, axes, shape)
  }

  class View(val handle: Array[Float], val shape: Array[Int], val offset: Int, val stride: Array[Int]) extends UntypedDenseTensor {
    def typeWith[A <: HList](axes: A) = new DenseTensor.View[A](handle, axes, shape, offset, stride)
  }


}
