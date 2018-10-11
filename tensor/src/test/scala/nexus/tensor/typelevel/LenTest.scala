package nexus.tensor.typelevel

import nexus.tensor._
import org.scalatest._
import shapeless._

/**
 * @author Frank van Lankvelt
 * @author Tongfei Chen
 */
class LenTest extends FunSuite {

  class A extends Dim; val A = new A
  class B extends Dim; val B = new B
  class C extends Dim; val C = new C

  test("Len should calculate length of HList") {
    assert(Len[$].apply == 0)
    assert(Len[A :: $].apply == 1)
    assert(Len[A :: B :: C ::$].apply == 3)
  }

  test("Len should calculate length of human-readable types") {

    assert(Len[Unit].apply == 0)
    assert(Len[A].apply == 1)
    assert(Len[(A, B, C)].apply == 3)
  }

}
