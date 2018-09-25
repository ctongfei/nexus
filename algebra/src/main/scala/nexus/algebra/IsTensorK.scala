package nexus.algebra

import nexus._
import nexus.algebra.typelevel._
import nexus.algebra.util._
import shapeless._

import scala.reflect._

/**
 * Typeclass that describes the algebraic structures on tensors with arbitrary element type.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait IsTensorK[T[_], E] extends TypeK[T] { self =>

  type ElementTag[E]

  /** Returns the type tag associated with the element type of this tensor. */
  def elementType: ElementTag[E]

  def elementClassTag: ClassTag[E]

  def rank(x: T[_]): Int

  def shape(x: T[_]): Seq[Int]

  def size(x: T[_]): Int

  def get(x: T[_], is: Seq[Int]): E

  def set(x: T[_], is: Seq[Int], v: E): Unit

  def newTensor[A](shape: Seq[Int]): T[A]

  def fromFlatArray[A](array: Array[E], shape: Seq[Int]): T[A]

  def fromNestedArray[A, N <: Nat, Arr](axes: A)(array: Arr)(implicit nest: Nest.Aux[Arr, E, N], len: Len.Aux[A, N]) =
    fromFlatArray[A](nest.flatten(array), nest.shape(array))

  def wrapScalar(x: E): T[Unit]

  def unwrapScalar(x: T[Unit]): E

  def tabulateA[A](shape: Array[Int])(f: Array[Int] => E): T[A] = {
    val flatArray = Indices.indices(shape).map(f).toArray(elementClassTag)
    fromFlatArray(flatArray, shape)
  }

  def tabulate[A](n: Int)(f: Int => E): T[A] =
    tabulateA(Array(n))((a: Array[Int]) => f(a(0)))

  def tabulate[A, B](m: Int, n: Int)(f: (Int, Int) => E): T[(A, B)] =
    tabulateA(Array(m, n))((a: Array[Int]) => f(a(0), a(1)))

  def tabulate[A, B, C](n0: Int, n1: Int, n2: Int)(f: (Int, Int, Int) => E): T[(A, B, C)] =
    tabulateA(Array(n0, n1, n2))((a: Array[Int]) => f(a(0), a(1), a(2)))

  def map[A](x: T[A])(f: E => E): T[A]

  def map2[A](x1: T[A], x2: T[A])(f: (E, E) => E): T[A]

  def map3[A](x1: T[A], x2: T[A], x3: T[A])(f: (E, E, E) => E): T[A]

  def sliceAlong[A, U, B](x: T[A], axis: U, n: Int)(implicit rx: Remove.Aux[A, U, B]): T[B]

  def unstackAlong[A, U, B](x: T[A], axis: U)(implicit rx: Remove.Aux[A, U, B]): Seq[T[B]]

  def expandDim[A, I <: Nat, X <: Dim, B](x: T[A])(implicit ix: InsertAt.Aux[A, I, X, B]): T[B]

  def renameAxis[A, B](x: T[A]): T[B]

  def ground[A]: IsTensor[T[A], E]

}

trait IsTensor[T, E] extends Type[T] {

  /** Returns the type tag associated with the element type of this tensor. */
  def elementType: Type[E]
}
