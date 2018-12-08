package nexus.tensor

import nexus._
import nexus.tensor.typelevel._
import nexus.tensor.util._
import shapeless._

import scala.reflect._

/**
 * Typeclass that describes the algebraic structures on tensors with arbitrary element type.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait IsTensorK[T[_], @specialized E] { self =>

  type ElementTag[E]
  type TensorTag[te] <: IsTensor[te, E]

  /** Returns the type tag associated with the element type of this tensor. */
  def elementType: ElementTag[E]

  def elementClassTag: ClassTag[E]

  def rank(x: T[_]): Int

  def shape(x: T[_]): Seq[Int]

  def size(x: T[_]): Int

  def get(x: T[_], is: Seq[Int]): E

  def set(x: T[_], is: Seq[Int], v: E): Unit

  def newTensor[a](shape: Seq[Int]): T[a]

  def fromFlatArray[a](array: Array[E], shape: Seq[Int]): T[a]

  def fromNestedArray[a, N <: Nat, Arr](axes: a)(array: Arr)(implicit nest: Nest.Aux[Arr, E, N], len: Len.Aux[a, N]) =
    fromFlatArray[a](nest.flatten(array), nest.shape(array))

  def wrapScalar(x: E): T[Unit]

  def unwrapScalar(x: T[Unit]): E

  def tabulateA[a](shape: Array[Int])(f: Array[Int] => E): T[a] = {
    val flatArray = Indices.indices(shape).map(f).toArray(elementClassTag)
    fromFlatArray(flatArray, shape)
  }

  def tabulate[a](n: Int)(f: Int => E): T[a] =
    tabulateA(Array(n))((a: Array[Int]) => f(a(0)))

  def tabulate[a, b](m: Int, n: Int)(f: (Int, Int) => E): T[(a, b)] =
    tabulateA(Array(m, n))((a: Array[Int]) => f(a(0), a(1)))

  def tabulate[a, b, c](n0: Int, n1: Int, n2: Int)(f: (Int, Int, Int) => E): T[(a, b, c)] =
    tabulateA(Array(n0, n1, n2))((a: Array[Int]) => f(a(0), a(1), a(2)))

  def map[a](x: T[a])(f: E => E): T[a]

  def map2[a](x1: T[a], x2: T[a])(f: (E, E) => E): T[a]

  def map3[a](x1: T[a], x2: T[a], x3: T[a])(f: (E, E, E) => E): T[a]


  def transpose[U, V](x: T[(U, V)]): T[(V, U)]

  def sliceAlong[U, X, V](x: T[U], axis: X, n: Int)(implicit rx: Remove.Aux[U, X, V]): T[V]

  def unstackAlong[U, X, V](x: T[U], axis: X)(implicit rx: Remove.Aux[U, X, V]): Seq[T[V]]

  def expandDim[U, I <: Nat, X <: Dim, V](x: T[U])(implicit ix: InsertAt.Aux[U, I, X, V]): T[V]

  def renameAxis[U, V](x: T[U]): T[V]

  def ground[A]: TensorTag[T[A]]

}

trait IsTensor[T, E] {

}
