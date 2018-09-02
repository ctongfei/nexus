package nexus.ops.properties

import nexus._
import nexus.algebra._
import nexus.prob._

/**
 * A property that says the numeric differentiation of an operator should approximately
 * equal to the automatic differentiation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class ApproxProp[X, R](
                                 val op: AnyOp,
                                 val gen: Stochastic[X],
                                 val numSamples: Int = 100,
                                 val relativeDiff: Double = 0.001,
                                 val tolerableRelativeError: Double = 0.01,
                                 val tolerablePassedRatio: Double = 0.95,
)(implicit R: IsReal[R]) {

  def autoGrad(x: X): X
  def numGrad(x: X): X
  def error(ag: X, ng: X): R

  def passedCheck(): Boolean = {
    val samples = gen.repeatToSeq(numSamples).sample
    val numPassedCheck = samples count { x =>
      val e = error(autoGrad(x), numGrad(x))
      val passed = R.toFloat(e) < tolerableRelativeError
      if (!passed) println(s" - [WARNING!] ${op.name}($x): Relative error = $e")
      else         println(s" - [   OK   ] ${op.name}($x): Relative error = $e")
      passed
    }

    val ratioPassedCheck = numPassedCheck.toFloat / numSamples
    println(s"${op.name} passed check with probability $ratioPassedCheck")
    ratioPassedCheck >= tolerablePassedRatio
  }

}
