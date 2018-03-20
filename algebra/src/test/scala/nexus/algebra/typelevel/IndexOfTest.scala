package nexus.algebra.typelevel

import org.scalatest.FunSuite
import shapeless._

/**
 * @author Frank van Lankvelt
 */
class IndexOfTest extends FunSuite {

  test("IndexOf should find the index of a type in an HList") {
    type X = Int :: String :: HNil
    assert(IndexOf[X, Int].toInt == 0)
    assert(IndexOf[X, String].toInt == 1)
  }

  test("IndexOf should find the index of a type in a Tuple") {
    type X = (Int, String)
    assert(IndexOf[X, Int].toInt == 0)
    assert(IndexOf[X, String].toInt == 1)
  }
}
