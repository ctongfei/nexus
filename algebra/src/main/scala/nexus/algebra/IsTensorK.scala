package nexus.algebra

import nexus._
import nexus.algebra.typelevel._
import nexus.algebra.util._
import shapeless._
import shapeless.ops.nat._

/**
 * Typeclass that describes the algebraic structures on tensors with arbitrary element type.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait IsTensorK[T[_], E] extends TypeK[T] { self =>

  /** Type of untyped handle. */
  type H

  val H: IsUntypedTensor[H, E]

  /** Returns the type tag associated with the element type of this tensor. */
  def elementType: Type[E]

  /** Returns an untyped handle of a tensor by removing the axis type information. */
  def untype(x: T[_]): H

  /** Attaches axis information (an HList) to an untyped handle. */
  def typeWith[A](x: H): T[A]

  def rank(x: T[_]) = H.rank(untype(x))

  def shape(x: T[_]) = H.shape(untype(x))

  def size(x: T[_]) = H.size(untype(x))

  def get(x: T[_], is: Array[Int]): E
    = H.get(untype(x), is)

  def set(x: T[_], is: Array[Int], v: E): Unit

  def newTensor[A](shape: Array[Int]): T[A]

  def fromFlatArray[A](array: Array[E], shape: Array[Int]): T[A] =
    typeWith[A](H.fromFlatArray(array, shape))

  def fromNestedArray[A, N <: Nat, Arr](axes: A)(array: Arr)(implicit nest: Nest.Aux[Arr, E, N], len: Len.Aux[A, N]) =
    fromFlatArray[A](nest.flatten(array), nest.shape(array))

  def wrapScalar(x: E): T[Unit] = typeWith[Unit](H.wrapScalar(x))

  def unwrapScalar(x: T[Unit]): E = H.unwrapScalar(untype(x))

  def tabulate[A](shape: Array[Int])(f: Array[Int] => E): T[A] = {
    val flatArray = Indices.indices(shape).map(f).toArray(H.elementTypeClassTag)
    fromFlatArray(flatArray, shape)
  }

  def tabulate[A](n: Int)(f: Int => E): T[A] =
    tabulate(Array(n))((a: Array[Int]) => f(a(0)))

  def tabulate[A, B](m: Int, n: Int)(f: (Int, Int) => E): T[(A, B)] =
    tabulate(Array(m, n))((a: Array[Int]) => f(a(0), a(1)))

  def tabulate[A, B, C](n0: Int, n1: Int, n2: Int)(f: (Int, Int, Int) => E): T[(A, B, C)] =
    tabulate(Array(n0, n1, n2))((a: Array[Int]) => f(a(0), a(1), a(2)))

  def map[A](x: T[A])(f: E => E): T[A] =
    typeWith[A](H.map(untype(x))(f))

  def map2[A](x1: T[A], x2: T[A])(f: (E, E) => E): T[A] =
    typeWith[A](H.map2(untype(x1), untype(x2))(f))

  def map3[A](x1: T[A], x2: T[A], x3: T[A])(f: (E, E, E) => E): T[A] =
    typeWith[A](H.map3(untype(x1), untype(x2), untype(x3))(f))

  def expandDim[A, I <: Nat, X, B](x: T[A])(ia: InsertAt.Aux[A, I, X, B]): T[B] =
    typeWith[B](H.expandDim(untype(x), ia.index))

  def ground[A]: IsTensor[T[A], E]

}

trait IsTensor[T, E] extends Type[T] {

  /** Returns the type tag associated with the element type of this tensor. */
  def elementType: Type[E]
}
