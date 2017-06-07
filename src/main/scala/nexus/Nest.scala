package nexus

import shapeless._
import shapeless.nat._
import scala.reflect._

/**
 * Witnesses that type [[T]] is the [[N]]-dimensional nested array of type [[A]].
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Nest[T, A, N <: Nat] {
  def classTagA: ClassTag[A]
  def classTagT: ClassTag[T]
  def newArray(ns: Int*): T
  def dim: Int
  def flatten(a: T): Array[A]
  def nest(a: Array[A], ns: Int*): T
  def shape(a: T): Array[Int]
}

object Nest {

  implicit def case1[A <: AnyVal](implicit ct: ClassTag[A]): Nest[Array[A], A, _1] = new Nest[Array[A], A, _1] {
    def classTagA = ct
    def classTagT = ct.wrap
    def newArray(ns: Int*) = ct.newArray(ns.head)
    def dim = 1
    def flatten(a: Array[A]) = a
    def nest(a: Array[A], ns: Int*) = a
    def shape(a: Array[A]) = Array(a.length)
  }

  implicit def caseN[T, A, N <: Nat](implicit t: Nest[T, A, N]): Nest[Array[T], A, Succ[N]] = new Nest[Array[T], A, Succ[N]] {
    def classTagA = t.classTagA
    def classTagT = t.classTagT.wrap
    def newArray(ns: Int*) = Array.tabulate(ns.head)(i => t.newArray(ns.tail: _*))(t.classTagT)
    def dim = t.dim + 1
    def flatten(a: Array[T]) = a.flatMap(t.flatten).toArray(classTagA)
    def nest(a: Array[A], ns: Int*) = {
      val sliceSize = a.length / ns.head
      Array.tabulate(ns.head)(i => t.nest(a.slice(sliceSize * i, sliceSize * (i + 1)), ns.tail: _*))(t.classTagT)
    }
    def shape(a: Array[T]) = a.length +: t.shape(a.head)
  }

}
