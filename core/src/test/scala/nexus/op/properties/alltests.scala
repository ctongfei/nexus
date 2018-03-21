package nexus.op.properties

import nexus.algebra._
import nexus.algebra.syntax._
import nexus.prob._
import org.scalatest._

/**
 * @author Tongfei Chen
 */
object GenX {

  val genFloat = Normal(0f, 10f)
  val genDouble = Normal(0d, 10d)

}

class OpSSFloatTests extends OpSSTests(GenX.genFloat)
class OpSSDoubleTests extends OpSSTests(GenX.genDouble)

class OpSSSFloatTests extends OpSSSTests(GenX.genFloat)
