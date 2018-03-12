package nexus.algebra.typelevel

import org.scalatest.FunSuite
import shapeless._

class InsertAtSuite extends FunSuite {

  test("InsertAt should insert a type in an HList") {
    type X = Int :: String :: HNil
    val insertAt = InsertAt[X, Nat._1, Boolean]
    val out = insertAt(1 :: "two" :: HNil, true)
    assert(out == 1 :: true :: "two" :: HNil)
  }

  test("InsertAt should insert a type in a Tuple") {
    type X = (Int, String)

    // FIXME: can this implicit val be replaced by an import?
    implicit val rToHlist = ToHList[(Int, Boolean, String)]

    val insertAt = InsertAt[X, Nat._1, Boolean]
    val out = insertAt((1, "two"), true)
    assert(out == (1, true, "two"))
  }
}
