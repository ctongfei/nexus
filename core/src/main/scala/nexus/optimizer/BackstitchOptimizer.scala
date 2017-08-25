package nexus.optimizer

import nexus._
import nexus.op._

/**
 * Backstitch optimizer.
 *
 * Reference:
 * Y Wang, H Hadian, S Ding, K Li, H Xu, X Zhang, D Povey, S Khudanpur:
 * Backstitch: Counteracting Finite-sample Bias via Negative Steps.
 *
 * @param ν Learning rate
 * @param α Relative size of negative step
 * @param n One negative step every ''n'' steps
 * @author Tongfei Chen
 */
class BackstitchOptimizer(ν: Double, α: Double = 0.3, n: Int = 2) extends FirstOrderOptimizer {

  def updateParam[X](p: Param[X], g: X) = {
    implicit val ops = p.gradOps

    if (t % n == 0) { // negative step!
      p += g :* ((n - 1) * α * ν)
    }
    else
      p -= g :* ((1 + α) * ν)
  }

}
