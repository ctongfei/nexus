package nexus.tensor.typelevel

import nexus.tensor._
import org.scalatest._
import shapeless._

/**
 * @author Frank van Lankvelt
 */
class IndexOfTest extends FunSuite {

  class A extends Dim
  class B extends Dim

  test("IndexOf should find the index of a type in an HList") {
    type X = A :: B :: HNil
    assert(IndexOf[X, A].toInt == 0)
    assert(IndexOf[X, B].toInt == 1)
  }

  test("IndexOf should find the index of a type in a Tuple") {
    type X = (A, B)
    assert(IndexOf[X, A].toInt == 0)
    assert(IndexOf[X, B].toInt == 1)
  }
}
