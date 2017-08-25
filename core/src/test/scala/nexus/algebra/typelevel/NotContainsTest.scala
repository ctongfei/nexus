package nexus.algebra.typelevel

import nexus.$
import shapeless._

/**
 * @author Tongfei Chen
 */
object NotContainsTest extends App {

  class A; val A = new A
  class B; val B = new B
  class C; val C = new C

  val t1 = SymDiff[A::B::$, B::C::$]
  val t2 = SymDiff[A::C::$, B::C::$]
  val t3 = SymDiff[A::C::$, A::B::$]
  val t4 = SymDiff[A::B::$, $]
  val t5 = SymDiff[$, A::B::$]

  val bp = 0

}
