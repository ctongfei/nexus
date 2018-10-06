package nexus.jvm

import nexus.tensor.instances.all._
import nexus.tensor.typelevel.util._
import shapeless.Nat

import scala.reflect._

/**
 * A tensor in JVM with element type E, and axis descriptor A.
 * @author Tongfei Chen
 */
class Tensor[@specialized(Int, Long, Float, Double) E, A](
  val handle: Array[E],
  val shape: Array[Int],
  val stride: Array[Int],
  val offset: Int
) { self =>

  def rank = shape.length

  def size = ShapeUtils.product(shape)

  def index(indices: Seq[Int]) = {
    // Logically this is `offset + (indices zipWith strides)(_*_).sum`
    var i = offset
    var k = 0
    while (k < shape.length) {
      i += indices(k) * stride(k)
      k += 1
    }
    i
  }

  def apply(indices: Int*): E = handle(index(indices))

  def update(indices: Int*)(value: E) = handle(index(indices)) = value

  override def toString: String = {
    if (rank == 0) return handle(offset).toString
    val sb = new StringBuilder
    var yi = 0
    var xi = offset
    var d = rank - 1
    sb.append("[" * rank)
    val indices = Array.fill(rank)(0)
    while (yi < size) {
      sb.append(handle(xi))
      sb.append("\t")
      xi += stride(d)
      indices(d) += 1
      if (indices(d) >= shape(d) && d > 0) {
        while (indices(d) >= shape(d) && d > 0) {
          indices(d) = 0
          d -= 1
          sb.append("]\n")
          indices(d) += 1
          xi += stride(d) - (stride(d + 1) * shape(d + 1))
        }
        sb.append("[" * (rank - 1 - d))
        d = rank - 1
      }
      yi += 1
    }
    sb.toString()
  }

}


class FloatTensor[A](
                      handle: Array[Float],
                      shape: Array[Int],
                      stride: Array[Int],
                      offset: Int
                    ) extends Tensor[Float, A](handle, shape, stride, offset)

object FloatTensor extends JvmIsRealTensorK[Float, FloatTensor] {
  def newTensor[A](handle: Array[Float], shape: Array[Int], stride: Array[Int], offset: Int) =
    new FloatTensor[A](handle, shape, stride, offset)
}

class DoubleTensor[A](
                     handle: Array[Double],
                     shape: Array[Int],
                     stride: Array[Int],
                     offset: Int
                     ) extends Tensor[Double, A](handle, shape, stride, offset)

object DoubleTensor extends JvmIsRealTensorK[Double, DoubleTensor] {
  def newTensor[A](handle: Array[Double], shape: Array[Int], stride: Array[Int], offset: Int) =
    new DoubleTensor[A](handle, shape, stride, offset)
}
