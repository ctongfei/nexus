package nexus.algebra

import nexus._
import nexus.algebra.typelevel._
import shapeless.Nat
import shapeless.ops.hlist._
import shapeless.ops.nat._

/**
 * Typeclass that describes the algebraic structures on tensors with arbitrary element type.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait IsTensorH[T[_ <: $$], E] extends TypeH[T] { self =>

  /** Type of untyped handle. */
  type H

  val H: IsUntypedTensor[H, E]

  /** Returns the type tag associated with the element type of this tensor. */
  def elementType: Type[E]

  /** Returns an untyped handle of a tensor by removing the axis type information. */
  def untype(x: T[_]): H

  /** Attaches axis information (an HList) to an untyped handle. */
  def typeWith[A <: $$](x: H): T[A]

  def newTensor[A <: $$](shape: Array[Int]): T[A]

  def fromFlatArray[A <: $$](array: Array[E], shape: Array[Int]): T[A] =
    typeWith[A](H.fromFlatArray(array, shape))

  def fromNestedArray[A <: $$, N <: Nat, Arr](axes: A)(array: Arr)(implicit nest: Nest[Arr, E, N], len: Length.Aux[A, N]) =
    fromFlatArray[A](nest.flatten(array), nest.shape(array))

  def wrapScalar(x: E): T[$] = typeWith[$](H.wrapScalar(x))

  def unwrapScalar(x: T[$]): E = H.unwrapScalar(untype(x))

  def map[A <: $$](x: T[A])(f: E => E): T[A] =
    typeWith[A](H.map(untype(x))(f))

  def map2[A <: $$](x1: T[A], x2: T[A])(f: (E, E) => E): T[A] =
    typeWith[A](H.map2(untype(x1), untype(x2))(f))

  def map3[A <: $$](x1: T[A], x2: T[A], x3: T[A])(f: (E, E, E) => E): T[A] =
    typeWith[A](H.map3(untype(x1), untype(x2), untype(x3))(f))

  def expandDim[A <: $$, I <: Nat, X, B <: $$](x: T[A])(implicit i: ToInt[I], ia: InsertAt.Aux[A, I, X, B]): T[B] =
    typeWith[B](H.expandDim(untype(x), i()))

  def ground[A <: $$]: IsTensor[T[A], E]

}

trait IsTensor[T, E] extends Type[T]
