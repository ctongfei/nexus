package nexus.op.properties

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.prob._
import org.scalatest._

/**
 * @author Tongfei Chen
 */
class OpSSProps[R](op: Op1[R, R],
                   gen: Gen[R],
                   numSamples: Int = 100,
                   relativeDiff: Double = 0.001,
                   tolerableError: Double = 0.01,
                   tolerablePassedRatio: Double = 0.95
                  )(implicit R: IsReal[R]) {

  import R._

  val genSeq = gen.seq(numSamples)

  def autoGrad(x: R) =
    op.backward(one, op.forward(x), x)

  def numGrad(x: R) = {
    val δ = x * relativeDiff
    (op.forward(x + δ) - op.forward(x - δ)) / (δ * 2)
  }

  def passedCheck(): Boolean = {
    val samples = genSeq.sample
    val numPassedCheck = samples count { x =>
      val error = abs(autoGrad(x) - numGrad(x)) / abs(autoGrad(x))
      R.toFloat(error) < tolerableError
    }

    val ratioPassedCheck = numPassedCheck.toFloat / numSamples

    println(s"${op.name} passed check with probability $ratioPassedCheck")

    ratioPassedCheck >= tolerablePassedRatio
  }

}
