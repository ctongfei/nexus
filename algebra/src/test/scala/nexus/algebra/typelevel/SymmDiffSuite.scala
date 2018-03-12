package nexus.algebra.typelevel

import org.scalatest.FunSuite
import shapeless._

class SymmDiffSuite extends FunSuite {

  test("SymDiff of HNil and any type should retain all rhs types") {
    class A
    val a = new A
    val symdiff = SymDiff[HNil, A :: HNil]

    assert(symdiff(HNil, a :: HNil) == a :: HNil)
    assert(symdiff.matchedIndices == Nil)
    assert(symdiff.lhsRetainedIndices == Nil)
    assert(symdiff.rhsRetainedIndices == List((0, 0)))
  }

  test("SymDiff finds common types") {
    class A
    val a = new A
    class B
    val b = new B

    val sdNone = SymDiff[A :: HNil, B :: HNil]
    assert(sdNone(a :: HNil, b :: HNil) == a :: b :: HNil)
    assert(sdNone.matchedIndices == Nil)
    assert(sdNone.lhsRetainedIndices == List((0, 0)))
    assert(sdNone.rhsRetainedIndices == List((1, 0)))

    val sdBA = SymDiff[A :: HNil, B :: A :: HNil]
    assert(sdBA(a :: HNil, b :: a :: HNil) == b :: HNil)
    assert(sdBA.matchedIndices == List((0, 1)))
    assert(sdBA.lhsRetainedIndices == Nil)
    assert(sdBA.rhsRetainedIndices == List((0, 0)))

    val sdAB = SymDiff[A :: HNil, A :: B :: HNil]
    assert(sdAB(a :: HNil, a :: b :: HNil) == b :: HNil)
    assert(sdAB.matchedIndices == List((0, 0)))
    assert(sdAB.lhsRetainedIndices == Nil)
    assert(sdAB.rhsRetainedIndices == List((0, 1)))
  }

}
