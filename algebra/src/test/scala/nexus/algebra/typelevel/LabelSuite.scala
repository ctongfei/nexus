package nexus.algebra.typelevel

import org.scalatest.FunSuite

class LabelSuite extends FunSuite {

  test("Label allows a regular class to be used as a Label") {
    class Foo

    val label = Label[Foo]
  }
}
