package nexus

import shapeless._
import shapeless.ops.hlist._

/**
 * Factories for constructing dense tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait TypedTensorFactory[T[_ <: $$], E] {

  def scalar(x: E): T[$]

  def fill[A <: $$](value: => E, axes: A, shape: Array[Int]): T[A]

  def fromFlatArray[A <: $$](array: Array[E], axes: A, shape: Array[Int]): T[A]

  def fromNestedArray[A <: $$, N <: Nat, T]
    (axes: A)(array: T)(implicit nest: Nest[T, E, N], nn: Length.Aux[A, N]) =
    fromFlatArray(nest.flatten(array), axes, nest.shape(array))

}
