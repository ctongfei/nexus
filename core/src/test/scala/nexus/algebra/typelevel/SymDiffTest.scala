package nexus.algebra.typelevel

import shapeless._

/**
 * @author Tongfei Chen
 */
object SymDiffTest extends App {

  class A; val A = new A
  class B; val B = new B
  class C; val C = new C

  val e: HNil = HNil
  val a = A :: HNil
  val b = B :: HNil
  val ab = A :: B :: HNil
  val bc = B :: C :: HNil

  def symDiff[A <: HList, B <: HList, C <: HList]
  (a: A, b: B)(implicit s: SymDiff.Aux[A, B, C]) = s

  val sab = symDiff(e, e)

  val xxbp = 0

}
