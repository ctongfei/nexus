package nexus

import nexus.typelevel._
import shapeless._

/**
 * Contains shared methods for creating tensors.
 * This should be inherited by companion objects of various tensor types
 * to provide easy access to various constructors of tensors.
 * @author Tongfei Chen
 */
abstract class TensorFactory[T[_], E](val T: IsTensorK[T, E]) {

  /**
   * Creates a vector (1-dim tensor) with the specified label and size.
   * @example {{{ FloatTensor.newVector(Length -> 30) }}}
   */
  def newVector[I <: Dim](a: (I, Int)): T[I] =
    T.newTensor[I](List(a._2))

  /**
   * Creates a matrix (2-dim tensor) with the specified labels and sizes.
   * @example {{{ FloatTensor.newMatrix(Frame -> 1000, Channel -> 40) }}}
   */
  def newMatrix[I <: Dim, J <: Dim](a: (I, Int), b: (J, Int)): T[(I, J)] =
    T.newTensor[(I, J)](List(a._2, b._2))

  /**
   * Creates a tensor (with arbitrary rank) with the specified labels and sizes.
   * @example {{{ FloatTensor.newTensor(Width -> 128, Height -> 128, Channel -> 3) }}}
   */
  def newTensor[S, U](shape: S)(implicit s: SizedAxes.Aux[S, U]): T[U] =
    T.newTensor[U](s.shape(shape))

  /**
   * Creates a tensor (with arbitrary rank) using a JVM nested array.
   * @example {{{
   *           FloatTensor.fromNestedArray(A, B)(
   *             Array(
   *               Array(1f, 2f),
   *               Array(3f, 4f)
   *             )
   *           ) // return type: FloatTensor[(A, B)]
   * }}}
   */
  def fromNestedArray[U, N <: Nat, Arr](axes: U)(array: Arr)(implicit nest: Nest.Aux[Arr, E, N], len: Len.Aux[U, N]): T[U] =
    T.fromNestedArray(axes)(array)

}
