package nexus

import nexus._
import nexus.typelevel._
import nexus.util._
import shapeless._

import scala.collection._
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

  def rank[I](x: T[I]): Int

  def sizeOfDim[I](x: T[I], dim: Int): Int

  def shape[I](x: T[I]): immutable.IndexedSeq[Int] = new immutable.IndexedSeq[Int] {
    def length = rank(x)
    def apply(idx: Int) = sizeOfDim(x, idx)
  }

  def numElements[I](x: T[I]): Int = shape(x).product

  def get[I](x: T[I], is: Seq[Int]): E

  def set[I](x: T[I], is: Seq[Int], v: E): Unit


  // Construction methods
  def newTensor[I](shape: Seq[Int]): T[I]

  def fromFlatArray[I](array: Array[E], shape: Seq[Int]): T[I]

  def fromNestedArray[I, N <: Nat, Arr](axes: I)(array: Arr)(implicit nest: Nest.Aux[Arr, E, N], len: Len.Aux[I, N]) =
    fromFlatArray[I](nest.flatten(array), nest.shape(array))

  def wrapScalar(x: E): T[Unit]

  def unwrapScalar(x: T[Unit]): E

  def tabulateA[I](shape: Array[Int])(f: Array[Int] => E): T[I] = {
    val flatArray = Indices.indices(shape).map(f).toArray(elementClassTag)
    fromFlatArray(flatArray, shape)
  }

  def tabulate[I](n: Int)(f: Int => E): T[I] =
    tabulateA(Array(n))((a: Array[Int]) => f(a(0)))

  def tabulate[I, J](m: Int, n: Int)(f: (Int, Int) => E): T[(I, J)] =
    tabulateA(Array(m, n))((a: Array[Int]) => f(a(0), a(1)))

  def tabulate[I, J, K](n0: Int, n1: Int, n2: Int)(f: (Int, Int, Int) => E): T[(I, J, K)] =
    tabulateA(Array(n0, n1, n2))((a: Array[Int]) => f(a(0), a(1), a(2)))

  def map[I](x: T[I])(f: E => E): T[I]

  def map2[I](x1: T[I], x2: T[I])(f: (E, E) => E): T[I]

  def map3[I](x1: T[I], x2: T[I], x3: T[I])(f: (E, E, E) => E): T[I]


  def transpose[I, J](x: T[(I, J)]): T[(J, I)]

  def sliceAlong[U, I, V](x: T[U], axis: I, n: Int)(implicit rx: Remove.Aux[U, I, V]): T[V]

  def unstackAlong[U, I, V](x: T[U], axis: I)(implicit rx: Remove.Aux[U, I, V]): Seq[T[V]]

  def expandDim[U, I <: Nat, X <: Dim, V](x: T[U])(implicit ix: InsertAt.Aux[U, I, X, V]): T[V]

  def renameAxis[I, J](x: T[I]): T[J]

  def ground[I]: TensorTag[T[I]]

}

trait IsTensor[T, E] {

}
