package nexus

import nexus.typelevel._
import shapeless._

/**
 * Contains shared methods for creating tensors.
 * This should be inherited by companion objects of various tensor types.
 * @author Tongfei Chen
 */
abstract class TensorFactory[T[_], E](val T: IsTensorK[T, E]) {

  /**
   * Creates a vector (1-dim tensor) with the specified label and size.
   * @example {{{ FloatTensor.newVector(Length -> 30) }}}
   */
  def newVector[A <: Dim](a: (A, Int)): T[A] =
    T.newTensor[A](List(a._2))

  /**
   * Creates a matrix (2-dim tensor) with the specified labels and sizes.
   * @example {{{ FloatTensor.newMatrix(Frame -> 1000, Channel -> 40) }}}
   */
  def newMatrix[A <: Dim, B <: Dim](a: (A, Int), b: (B, Int)): T[(A, B)] =
    T.newTensor[(A, B)](List(a._2, b._2))

  /**
   * Creates a tensor (with arbitrary rank) with the specified labels and sizes.
   * @example {{{ FloatTensor.newTensor(Width -> 128, Height -> 128, Channel -> 3) }}}
   */
  def newTensor[S, A](shape: S)(implicit s: SizedAxes.Aux[S, A]): T[A] =
    T.newTensor[A](s.shape(shape))

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
  def fromNestedArray[A, N <: Nat, Arr](axes: A)(array: Arr)(implicit nest: Nest.Aux[Arr, E, N], len: Len.Aux[A, N]): T[A] =
    T.fromNestedArray(axes)(array)

}
