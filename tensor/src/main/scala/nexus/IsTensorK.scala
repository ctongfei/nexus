package nexus

import nexus._
import nexus.typelevel._
import nexus.util._
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

  def rank[A](x: T[A]): Int

  def shape[A](x: T[A]): IndexedSeq[Int] = new IndexedSeq[Int] {
    def length = rank(x)
    def apply(idx: Int) = sizeOfDim(x, idx)
  }

  def sizeOfDim[A](x: T[A], dim: Int): Int

  def numElements[A](x: T[A]): Int = shape(x).product

  def get[A](x: T[A], is: Seq[Int]): E

  def set[A](x: T[A], is: Seq[Int], v: E): Unit


  // Construction methods
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

  def tabulate[a, b](m: Int, n: Int)(f: (Int, Int) => E): T[(a, b)] =
    tabulateA(Array(m, n))((a: Array[Int]) => f(a(0), a(1)))

  def tabulate[a, b, c](n0: Int, n1: Int, n2: Int)(f: (Int, Int, Int) => E): T[(a, b, c)] =
    tabulateA(Array(n0, n1, n2))((a: Array[Int]) => f(a(0), a(1), a(2)))

  def map[A](x: T[A])(f: E => E): T[A]

  def map2[A](x1: T[A], x2: T[A])(f: (E, E) => E): T[A]

  def map3[A](x1: T[A], x2: T[A], x3: T[A])(f: (E, E, E) => E): T[A]


  def transpose[U, V](x: T[(U, V)]): T[(V, U)]

  def sliceAlong[U, X, V](x: T[U], axis: X, n: Int)(implicit rx: Remove.Aux[U, X, V]): T[V]

  def unstackAlong[U, X, V](x: T[U], axis: X)(implicit rx: Remove.Aux[U, X, V]): Seq[T[V]]

  def expandDim[U, I <: Nat, X <: Dim, V](x: T[U])(implicit ix: InsertAt.Aux[U, I, X, V]): T[V]

  def renameAxis[U, V](x: T[U]): T[V]

  def ground[A]: TensorTag[T[A]]

}

trait IsTensor[T, E] {

}
