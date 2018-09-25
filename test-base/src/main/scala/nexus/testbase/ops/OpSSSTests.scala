package nexus.testbase.ops

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.ops._
import nexus.prob._
import org.scalatest._

/**
 * Tests (R, R) -> R functions.
 * @author Tongfei Chen
 */
class OpSSSTests[R](gen: Stochastic[R])(implicit R: IsReal[R]) extends FunSuite {

  class Prop(op: Op2[R, R, R], gen: Stochastic[(R, R)]) extends ApproxProp[(R, R), R](op, gen) {
    import R._

    def autoGrad(x: (R, R)) = {
      val (x1, x2) = x
      val y = op.forward(x1, x2)
      (op.backward1(one, y, x1, x2), op.backward2(one, y, x1, x2))
    }

    def numGrad(x: (R, R)) = {
      val (x1, x2) = x
      val δ1 = x1 * relativeDiff
      val δ2 = x2 * relativeDiff
      val dx1 = (op.forward(x1 + δ1, x2) - op.forward(x1 - δ1, x2)) / (δ1 * 2)
      val dx2 = (op.forward(x1, x2 + δ2) - op.forward(x1, x2 - δ2)) / (δ2 * 2)
      (dx1, dx2)
    }

    def error(ag: (R, R), ng: (R, R)) = {
      val d = sqrt(sqr(ag._1 - ng._1) + sqr(ag._2 - ng._2))
      d / sqrt(sqr(ag._1) + sqr(ag._2))
    }
  }

  val opsOnReal: Seq[Op2[R, R, R]] = Seq(
    Add.addF[R],
    Sub.subF[R],
    Mul.mulF[R],
    Div.divF[R]
  )

  for (op <- opsOnReal) {
    test(s"${op.name}'s automatic derivative is close to its numerical approximation on $R") {
      val prop = new Prop(op, gen product gen)
      assert(prop.passedCheck())
    }
  }

}
