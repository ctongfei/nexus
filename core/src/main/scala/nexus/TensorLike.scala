package nexus

import shapeless._

/**
 * Base type for all tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait TensorLike[D, A <: HList, T <: TensorLike[D, A, T]] extends UntypedTensorLike[D, T] { self: T =>

  def shape: Array[Int]

  /**
   * Returns the axes descriptor of this tensor (An [[shapeless.HList]]).
   */
  def axes: A

  def apply(indices: Int*): D

  def stringPrefix: String

  override def toString = {
    val axesNames = axes.runtimeList.map(_.getClass.getSimpleName)
    val shapeString = (axesNames zip shape).map { case (axis, dim) => s"$axis($dim)"}.mkString(" :: ")
    val header = s"$stringPrefix[$shapeString]"
    val body = stringBody
    s"$header\n$body"
  }

}
