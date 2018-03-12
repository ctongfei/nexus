package nexus.algebra.typelevel

import org.scalatest.FunSuite
import shapeless._

class SymDiffSuite extends FunSuite {

  class A

  class B

  class C

  test("SymDiff should retain all rhs types when lhs is HNil") {
    class A
    val symdiff = SymDiff[HNil, A :: HNil]
    assert(symdiff.matchedIndices == Nil)
  }

  test("SymDiff should concatenate types without match") {
    val sdNone: SymDiff.Aux[A :: HNil, B :: HNil, A :: B :: HNil] =
      SymDiff[A :: HNil, B :: HNil]
    assert(sdNone.matchedIndices == Nil)

    val sdLeft: SymDiff.Aux[B :: HNil, A :: B :: HNil, A :: HNil] = sdNone.recoverLeft
    assert(sdLeft.matchedIndices == List((0, 1)))

    val sdRight: SymDiff.Aux[A :: B :: HNil, A :: HNil, B :: HNil] = sdNone.recoverRight
    assert(sdRight.matchedIndices == List((0, 0)))
  }

  test("SymDiff should filter out types that occur in both parents") {
    val sdBA: SymDiff.Aux[A :: HNil, B :: A :: HNil, B :: HNil] =
      SymDiff[A :: HNil, B :: A :: HNil]
    assert(sdBA.matchedIndices == List((0, 1)))

    val sdBALeft: SymDiff.Aux[B :: A :: HNil, B :: HNil, A :: HNil] = sdBA.recoverLeft
    assert(sdBALeft.matchedIndices == List((0, 0)))

    val sdBARight: SymDiff.Aux[B :: HNil, A :: HNil, B :: A :: HNil] = sdBA.recoverRight
    assert(sdBARight.matchedIndices == Nil)
  }

  test("SymDiff should calculate indices of deep matches") {
    val sdAB: SymDiff.Aux[A :: B :: HNil, C :: A :: B :: HNil, C :: HNil] =
      SymDiff[A :: B :: HNil, C :: A :: B :: HNil]
    assert(sdAB.matchedIndices == List((0, 1), (1, 2)))
  }

}
