package nexus.algebra.typelevel

import org.scalatest.FunSuite

class LabelTest extends FunSuite {

  test("Label allows a regular class to be used as a Label") {
    class Foo

    val label = Label[Foo]
  }
}
