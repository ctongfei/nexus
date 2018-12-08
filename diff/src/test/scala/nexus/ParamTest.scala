package nexus

import nexus.diff._
import nexus.tensor.instances._
import org.scalatest._

/**
 * @author Tongfei Chen
 */
class ParamTest extends FunSuite {

  test("Creating parameters with sourcecode.Name") {

    val x = Param(3.0)
    assert(x.name == "x")

    val myParam = Param(1.0f)
    assert(myParam.name == "myParam")

  }


}
