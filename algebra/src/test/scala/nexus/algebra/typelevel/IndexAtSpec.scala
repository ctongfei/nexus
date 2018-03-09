package nexus.algebra.typelevel

import org.scalatest.FlatSpec
import shapeless._

class IndexAtSpec extends FlatSpec {

  "The IndexAt typelevel function" should "insert a type in an HList" in {
    type X = Int :: String :: HNil
    val insertAt = InsertAt[X, Nat._1, Boolean]
    val out = insertAt(1 :: "two" :: HNil, true)
    assert(out == 1 :: true :: "two" :: HNil)
  }

  it should "insert a type in a Tuple" in {
    type X = (Int, String)

    // FIXME: can this implicit val be replaced by an import?
    implicit val rToHlist = ToHList[(Int, Boolean, String)]

    val insertAt = InsertAt[X, Nat._1, Boolean]
    val out = insertAt((1, "two"), true)
    assert(out == (1, true, "two"))
  }
}
