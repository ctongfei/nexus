package nexus.typelevel

import shapeless._
import shapeless.nat._

import scala.annotation._
import scala.reflect._

/**
 * Witnesses that type [[T]] is a multidimensional nested array of type [[E]].
 * This typeclass contains methods for working with nested arrays.
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("${T} is not a multidimensional JVM array of ${E}.")
trait Nest[T, E] {

  /** [[shapeless.Nat]] representation of the rank of this multidimensional array. */
  type Rank <: Nat

  /** ClassTag of the element type. */
  def elementClassTag: ClassTag[E]

  /** ClassTag of the nested array type. */
  def arrayClassTag: ClassTag[T]

  /** Creates new nested array by the given shape. */
  def newArray(ns: Int*): T

  /** Returns the rank of the nested array. */
  def rank: Int

  /** Flattens the nested array to a 1-dimensional array. */
  def flatten(a: T): Array[E]

  /** Nests a 1-dimensional array to a nested array by the given shape. */
  def nest(a: Array[E], ns: Int*): T

  /** Returns the shape of this nested array. */
  def shape(a: T): Array[Int]

}

object Nest {

  def apply[T, E](implicit n: Nest[T, E]): Aux[T, E, n.Rank] = n
  type Aux[T, E, R0 <: Nat] = Nest[T, E] { type Rank = R0 }

  // Nest.Aux[E, E, _0]
  implicit def case0[E](implicit ctE: ClassTag[E], na: E <:!< Array[_]): Nest.Aux[E, E, _0] =
    new Nest[E, E] {
      type Rank = _0
      def elementClassTag = ctE
      def arrayClassTag = ctE
      def newArray(ns: Int*) = null.asInstanceOf[E]
      def rank = 0
      def flatten(a: E) = Array(a)
      def nest(a: Array[E], ns: Int*) = {
        assert(a.length == 1 && ns.length == 0)
        a.head
      }
      def shape(a: E) = Array()
    }

  // Nest.Aux[Array[E], E, _1]
  implicit def case1[E](implicit ctE: ClassTag[E], na: E <:!< Array[_]): Nest.Aux[Array[E], E, _1] =
    new Nest[Array[E], E] {
      type Rank = _1
      def elementClassTag = ctE
      def arrayClassTag = ctE.wrap
      def newArray(ns: Int*) = ctE.newArray(ns.head)
      def rank = 1
      def flatten(a: Array[E]) = a
      def nest(a: Array[E], ns: Int*) = {
        assert(a.length == ns.head && ns.length == 1)
        a
      }
      def shape(a: Array[E]) = Array(a.length)
    }

  // Nest.Aux[T, E, N] => Nest.Aux[Array[T], E, Succ[N]]
  implicit def caseN[T, E, N <: Nat](implicit t: Nest.Aux[T, E, N]): Nest.Aux[Array[T], E, Succ[N]] =
    new Nest[Array[T], E] {
      type Rank = Succ[N]
      def elementClassTag = t.elementClassTag
      def arrayClassTag = t.arrayClassTag.wrap
      def newArray(ns: Int*) = Array.tabulate(ns.head)(_ => t.newArray(ns.tail: _*))(t.arrayClassTag)
      def rank = t.rank + 1
      def flatten(a: Array[T]) = a.flatMap(t.flatten).toArray(elementClassTag)
      def nest(a: Array[E], ns: Int*) = {
        val sliceSize = a.length / ns.head
        Array.tabulate(ns.head)(i => t.nest(a.slice(sliceSize * i, sliceSize * (i + 1)), ns.tail: _*))(t.arrayClassTag)
      }
      def shape(a: Array[T]) = a.length +: t.shape(a.head)
    }

}
