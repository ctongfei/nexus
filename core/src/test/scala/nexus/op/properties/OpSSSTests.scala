package nexus.op.properties

import nexus._
import nexus.algebra._
import nexus.op._
import nexus.prob._
import org.scalatest._

/**
 * @author Tongfei Chen
 */
class OpSSSTests[R](gen: Gen[R])(implicit R: IsReal[R]) extends FunSuite {

  val opsOnReal: Seq[Op2[R, R, R]] = Seq(
    Add.addF[R],
    Sub.subF[R],
    Mul.mulF[R],
    Div.divF[R]
  )

  for (op <- opsOnReal) {
    test(s"${op.name}'s automatic derivative is close to its numerical approximation on $R") {
      val prop = new OpSSSProps(op, gen)
      assert(prop.passedCheck())
    }
  }

}
