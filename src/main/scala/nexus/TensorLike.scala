package nexus

import shapeless.HList

/**
 * Base type for all tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait TensorLike[D, A <: HList, T <: TensorLike[D, A, T]] { self: T =>

  def shape: Array[Int]

  def axes: A

  def size = {
    var n = 1
    var k = 0
    while (k < rank) {
      n *= shape(k)
      k += 1
    }
    n
  }

  def rank = shape.length

  def apply(indices: Int*): D

  def stringPrefix: String
  def stringBody: String

  override def toString = {
    val axesNames = axes.runtimeList.map(_.getClass.getSimpleName)
    val shapeString = (axesNames zip shape).map { case (axis, dim) => s"$axis($dim)"}.mkString(" :: ")
    val header = s"$stringPrefix[$shapeString]"
    val body = stringBody
    s"$header\n$body"
  }

}
