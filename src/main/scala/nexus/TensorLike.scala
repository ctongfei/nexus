package nexus

import shapeless._
import shapeless.ops.hlist._

/**
 * Base trait for all tensors across different devices.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait TensorLike[T <: TensorLike[T]] { self: T =>

  /**
   * Phantom type of the elements in this tensor.
   */
  type Elem <: DType

  type Device <: DeviceLike[Device]

  /**
   * A heterogeneous list that names each dimension of this tensor.
   */
  type Axes <: HList

  /**
   * Type of the object for storing the actual tensor on devices.
   */
  type Handle

  /**
   * Type of the JVM representation of the elements.
   */
  type JvmElem

  /**
   * An object that marks the elements in this tensor.
   */
  val dtype: Elem

  /**
   * Reference to the device which this object depends on.
   */
  val device: Device

  val axes: Axes

  val shape: Seq[Int]
  val handle: Handle
  val bridge: Store[Elem, JvmElem, Handle]

  def apply(indices: Int*): JvmElem
  def update(indices: Int*)(newValue: JvmElem): Unit

  type Rank = Length[Axes]#Out
  def rank = shape.size

  def unary_- : T
  def unary_+ : T

  def +(that: T): T
  def -(that: T): T
  def |*|(that: T): T
  def |/|(that: T): T
  def :*(that: JvmElem): T

  override def toString = {
    val axesNames = axes.runtimeList.map(_.getClass.getSimpleName).init
    val shapeString = (axesNames zip shape).map { case (axis, dim) => s"$axis($dim)" }.mkString(" :: ")
    val header = s"Tensor[DType=$dtype, Shape=[$shapeString], Device=${device.name}]"
    header
  }
}

