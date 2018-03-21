package nexus.op.properties

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.prob._

/**
 * @author Tongfei Chen
 */
class OpSSSProps[R](op: Op2[R, R, R],
                    gen: Gen[R],
                    numSamples: Int = 100,
                    relativeDiff: Double = 0.001,
                    tolerableError: Double = 0.01,
                    tolerablePassedRatio: Double = 0.95,
                  )(implicit R: IsReal[R]) {

  import R._

  val genPairSeq = (gen product gen).seq(numSamples)

  def autoGrad(x1: R, x2: R) = {
    val y = op.forward(x1, x2)
    (op.backward1(one, y, x1, x2), op.backward2(one, y, x1, x2))
  }

  def numGrad(x1: R, x2: R) = {
    val δ1 = x1 * relativeDiff
    val δ2 = x2 * relativeDiff
    val dx1 = (op.forward(x1 + δ1, x2) - op.forward(x1 - δ1, x2)) / (δ1 * 2)
    val dx2 = (op.forward(x1, x2 + δ2) - op.forward(x1, x2 - δ2)) / (δ2 * 2)
    (dx1, dx2)
  }

  def norm(x1: R, x2: R) = R.sqrt(R.sqr(x1) + R.sqr(x2))

  def passedCheck(): Boolean = {
    val samples = genPairSeq.sample
    val numPassedCheck = samples count { case (x1, x2) =>
      val ag = autoGrad(x1, x2)
      val ng = numGrad(x1, x2)
      val error = (norm(ag._1 - ng._1, ag._2 - ng._2)) / norm(ag._1, ag._2)
      R.toFloat(error) < tolerableError
    }

    val ratioPassedCheck = numPassedCheck.toFloat / numSamples

    println(s"${op.name} passed check with probability $ratioPassedCheck")

    ratioPassedCheck >= tolerablePassedRatio
  }

}
