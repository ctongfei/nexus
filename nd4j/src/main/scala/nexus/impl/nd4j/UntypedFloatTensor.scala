package nexus.impl.nd4j

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4s.Implicits._


trait UntypedFloatTensor { self =>
  val handle: INDArray
  val stride: Array[Int] = handle.stride()
  val offset: Int = handle.offset().toInt
  val shape: Array[Int] = handle.shape()

  def rank = shape.length

  def index(indices: Seq[Int]): Int = {
    var i = offset
    var k = 0
    while (k < rank) {
      i += indices(k) * stride(k)
      k += 1
    }
    i
  }

  def size = handle.length

  def apply(indices: Int*) = handle(indices: _*)

  def update(indices: Int*)(value: Float) = handle(index(indices)) = value

  def sliceUntyped(n: Int, i: Int): UntypedFloatTensor =
    new UntypedFloatTensor.Tensor(handle.slice(i, n))

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

  class Tensor(val handle: INDArray) extends UntypedFloatTensor {
    def typeWith[A] = new FloatTensor.Tensor[A](handle)
  }

}