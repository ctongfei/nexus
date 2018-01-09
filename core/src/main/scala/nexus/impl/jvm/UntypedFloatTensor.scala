package nexus.impl.jvm

import nexus._
import nexus.algebra.typelevel.util._
import shapeless._

trait UntypedFloatTensor { self =>
  val handle: Array[Float]
  val stride: Array[Int]
  val offset: Int
  val shape: Array[Int]

  def rank = shape.length

  def index(indices: Seq[Int]) = {
    var i = offset
    var k = 0
    while (k < rank) {
      i += indices(k) * stride(k)
      k += 1
    }
    i
  }

  def size = shape.product

  def apply(indices: Int*) = handle(index(indices))

  def update(indices: Int*)(value: Float) = handle(index(indices)) = value

  def sliceUntyped(n: Int, i: Int): UntypedFloatTensor =
    new UntypedFloatTensor.View(
      handle = self.handle,
      shape = ShapeUtils.removeAt(self.shape, n),
      offset = self.offset + self.stride(n) * i,
      stride = ShapeUtils.removeAt(self.stride, n)
    )

  def typeWith[A]: FloatTensor[A]

  def stringBody: String = rank match {
    case 0 =>
      handle(offset).toString
    case 1 =>
      (0 until shape(0)).map { i => handle(offset + i * stride(0)) }.mkString("[", ", \t", "]")
    case _ =>
      (0 until shape(0)).map { i => sliceUntyped(0, i).stringBody }.mkString("[", "\n", "]")
  }

}

object UntypedFloatTensor {

  class Contiguous(val handle: Array[Float], val shape: Array[Int]) extends UntypedFloatTensor { self =>
    val stride = shape.scanRight(1)(_ * _).tail
    val offset = 0
    def typeWith[A] = new FloatTensor.Contiguous[A](handle, shape)
  }

  class View(val handle: Array[Float], val shape: Array[Int], val offset: Int, val stride: Array[Int]) extends UntypedFloatTensor {
    def typeWith[A] = new FloatTensor.View[A](handle, shape, offset, stride)
  }


}
