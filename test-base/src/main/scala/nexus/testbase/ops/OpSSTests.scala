package nexus.testbase.ops

import nexus.diff._
import nexus.diff.ops._
import nexus.prob._
import nexus._
import nexus.syntax._
import org.scalatest._

/**
 * Tests R -> R functions.
 * @author Tongfei Chen
 */
class OpSSTests[R](gen: Stochastic[R])(implicit R: IsReal[R], RToFloat: CastToFloat[R]) extends FunSuite {

  import R._
  import RToFloat._

  class Prop(op: Op1[R, R], gen: Stochastic[R]) extends ApproxProp[R, R](op, gen) {


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
  )

  val genOps: Seq[(Stochastic[R], Seq[Op1[R, R]])] = Seq(
    gen -> Seq(
      Neg.fR[R],
      Inv.fR[R],
      Sin.fR[R],
      Cos.fR[R],
      Exp.fR[R],
      Abs.fR[R],
      Sqr.fR[R]
    ),
    gen.filter(x => toFloat(x) > 0f) -> Seq(
      Log.fR[R],
      Sqrt.fR[R]
    )
  )

  for ((gen, ops) <- genOps)
    for (op <- ops) {
      test(s"${op.name}'s automatic derivative is close to its numerical approximation on $R") {
        assert(new Prop(op, gen).passedCheck())
      }
    }

}
