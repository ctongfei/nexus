package nexus.impl.nd4j

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4s.Implicits._


trait UntypedND4JTensor { self =>
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

  def apply(indices: Int*) = handle(index(indices))

  def update(indices: Int*)(value: Float) = handle(index(indices)) = value

  def sliceUntyped(n: Int, i: Int): UntypedND4JTensor =
    new UntypedND4JTensor.Tensor(handle.slice(i, n))

  def typeWith[A]: ND4JTensor[A]

  def stringBody: String = rank match {
    case 0 =>
      handle(offset).toString
    case 1 =>
      (0 until shape(0)).map { i => handle(offset + i * stride(0)) }.mkString("[", ", \t", "]")
    case _ =>
      (0 until shape(0)).map { i => sliceUntyped(0, i).stringBody }.mkString("[", "\n", "]")
  }

}


object UntypedND4JTensor {

  class Tensor(val handle: INDArray) extends UntypedND4JTensor {
    def typeWith[A] = new ND4JTensor.Tensor[A](handle)
  }

}