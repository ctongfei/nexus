package nexus

import nexus.util._
import shapeless.HList

/**
 * @author Tongfei Chen
 */
trait UntypedTensorLike[D, +T <: UntypedTensorLike[D, T]] { self: T =>

  def shape: Array[Int]

  /**
   * Returns the number of elements in this tensor.
   */
  def size = ShapeUtils.product(shape)

  /**
   * Returns the rank of this tensor.
   */
  def rank = shape.length

  def stringBody: String

  override def toString = stringBody

}
