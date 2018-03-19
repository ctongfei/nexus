package nexus.algebra.typelevel

import org.scalatest.FunSuite
import shapeless._

class LenSuite extends FunSuite {

  test("Len should calculate length of HList") {
    val len = Len[Int :: String :: HNil]
    assert(len() == 2)
  }

  test("Len should calculate length of Tuple") {
    val len = Len[(Int, String, Boolean)]
    assert(len() == 3)
  }

}
