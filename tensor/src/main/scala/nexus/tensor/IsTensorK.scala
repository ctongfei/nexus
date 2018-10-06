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
trait IsTensorK[T[_], E] { self =>

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

  def sliceAlong[a, u, b](x: T[a], axis: u, n: Int)(implicit rx: Remove.Aux[a, u, b]): T[b]

  def unstackAlong[a, u, b](x: T[a], axis: u)(implicit rx: Remove.Aux[a, u, b]): Seq[T[b]]

  def expandDim[a, i <: Nat, u <: Dim, b](x: T[a])(implicit ix: InsertAt.Aux[a, i, u, b]): T[b]

  def renameAxis[a, b](x: T[a]): T[b]

  def ground[a]: TensorTag[T[a]]

}

trait IsTensor[T, E] {

}
