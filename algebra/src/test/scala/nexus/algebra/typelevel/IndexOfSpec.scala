package nexus.algebra.typelevel

import org.scalatest.FlatSpec
import shapeless._

class IndexOfSpec extends FlatSpec {

  "The IndexOf typelevel function" should "find the index of a type in an HList" in {
    type X = Int :: String :: HNil
    assert(IndexOf[X, Int].toInt == 0)
    assert(IndexOf[X, String].toInt == 1)
  }

  it should "find the index in a Tuple" in {
    type X = (Int, String)
    assert(IndexOf[X, Int].toInt == 0)
    assert(IndexOf[X, String].toInt == 1)
  }
}
