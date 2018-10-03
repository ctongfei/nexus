package nexus.tensor.typelevel

import org.scalatest._
import shapeless._

/**
 * @author Frank van Lankvelt
 * @author Tongfei Chen
 */
class LenTest extends FunSuite {

  class A; val A = new A
  class B; val B = new B
  class C; val C = new C

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
