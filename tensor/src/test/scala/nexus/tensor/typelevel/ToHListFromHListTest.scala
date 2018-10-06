package nexus.tensor.typelevel

import nexus.tensor._
import org.scalatest._
import shapeless._

/**
 * @author Tongfei Chen
 */
class ToHListFromHListTest extends FunSuite {

  class a; val a = new a
  class b; val b = new b
  class c; val c = new c

  def toHList[A, Ah <: HList](a: A)(implicit t: ToHList.Aux[A, Ah]): Ah = t(a)

  def fromHList[A, Ah <: HList](ah: Ah)(implicit t: FromHList.Aux[Ah, A]): A = t(ah)

  test("ToHList should convert types to their canonical HList representation") {

    assert(toHList(()) == HNil)
    assert(toHList(1 -> 2) == 1 :: 2 :: HNil)
    assert(toHList("a", "b", "c") == "a" :: "b" :: "c" :: HNil)

  }

  test("FromHList should convert HLists to their corresponding human-readable singleton/tuple type") {

    assert(fromHList($) == ())
    assert(fromHList(1 :: $) == 1)
    assert(fromHList("a" :: HNil) == "a")
    assert(fromHList(a :: b :: $) == (a, b))

  }

}
