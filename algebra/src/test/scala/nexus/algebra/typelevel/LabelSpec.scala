package nexus.algebra.typelevel

import org.scalatest.FlatSpec

class LabelSpec extends FlatSpec {

  "A regular class" should "be accompanied by a Label" in {
    class Foo

    val label = Label[Foo]
  }
}
