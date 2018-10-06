package nexus.tensor.typelevel

import nexus.tensor._
import org.scalatest._
import shapeless._

/**
 * @author Tongfei Chen
 */
class Tests extends FunSuite {


  class a; val a = new a
  class b; val b = new b
  class c; val c = new c

  test("Len") {

    def len[A](a: A)(implicit l: Len[A]) = l()

    assert(Len[Unit].apply() == 0)
    assert(Len[String :: HNil].apply() == 1)
    assert(Len[a].apply() == 1)

    assert(len(()) == 0)
    assert(len(a) == 1)
    assert(len(1 -> 2.0) == 2)
    assert(len("a", "x") == 2)
    assert(len((1, 2, 3)) == 3)

  }

  test("IndexOf") {

    def indexOf[A, X, I <: Nat](a: A, x: X)(implicit i: IndexOf.Aux[A, X, I]) = i.toInt

    assert(indexOf(a::b::$, a) == 0)

  }

  test("NotContains") {

    def notContains[A, X](a: A, x: X)(implicit n: NotContains[A, X]) = n
    notContains(b::c::$, a)


  }

  test("Replace") {
    def replace[A, U, V, B](a: A, uv: (U, V))(implicit r: Replace.Aux[A, U, V, B]): B = r(a, uv._2)

    assert(replace(a::b::$, b -> c) == a::c::$)
    assert(replace((a, b), b -> c) == (a, c))
  }

  test("SymDiff") {

    def symDiff[A, B, C](a: A, b: B)(implicit sd: SymDiff.Aux[A, B, C]): C = sd(a, b)
    val x = symDiff(a::c::$, b::c::$)

  }

}
