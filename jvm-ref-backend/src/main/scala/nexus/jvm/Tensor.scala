package nexus.jvm

import nexus.algebra.typelevel._
import nexus.algebra.typelevel.util._
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
    k
  }

  def apply(indices: Int*): E = handle(index(indices))

  def update(indices: Int*)(value: E) = handle(index(indices)) = value


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
