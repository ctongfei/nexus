package nexus.algebra.typelevel

import org.scalatest.FlatSpec
import shapeless._

class LenSpec extends FlatSpec {

  "Len" should "calculate length of HList" in {
    val len = Len[Int :: String :: HNil]
    assert(len() == 2)
  }

  it should "calculate length of Tuple" in {
    val len = Len[(Int, String, Boolean)]
    assert(len() == 3)
  }

}
