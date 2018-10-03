package nexus.testbase.ops

import nexus._
import nexus.ops._
import nexus.prob._
import org.scalatest._

/**
 * Tests R -> R functions.
 * @author Tongfei Chen
 */
class OpSSTests[R](gen: Stochastic[R])(implicit R: IsReal[R]) extends FunSuite {

  class Prop(op: Op1[R, R], gen: Stochastic[R]) extends ApproxProp[R, R](op, gen) {

    import R._

    val genSeq = gen.repeatToSeq(numSamples)

    def autoGrad(x: R) =
      op.backward(one, op.forward(x), x)

    def numGrad(x: R) = {
      val δ = x * relativeDiff
      (op.forward(x + δ) - op.forward(x - δ)) / (δ * 2)
    }

    def error(ag: R, ng: R) = abs((ag - ng) / ag)

  }

  val opsOnReal: Seq[Op1[R, R]] = Seq(
    Id.idF[R],
    Neg.negF[R],
    Inv.scalarF[R],
    Sin.scalarF[R],
    Cos.scalarF[R],
    Exp.scalarF[R],
    Abs.scalarF[R],
    Sqr.scalarF[R]
  )

  for (op <- opsOnReal) {
    test(s"${op.name}'s automatic derivative is close to its numerical approximation on $R") {
      assert(new Prop(op, gen).passedCheck())
    }
  }

  val opsOnPositiveReal: Seq[Op1[R, R]] = Seq(
    Log.scalarF[R],
    Sqrt.scalarF[R]
  )

  for (op <- opsOnPositiveReal) {
    test(s"${op.name}'s automatic derivative is close to its numerical approximation on $R") {
      val prop = new Prop(op, gen.filter(x => R.toFloat(x) > 0))
      assert(prop.passedCheck())
    }
  }

}
