package nexus.optimizer

import nexus._
import nexus.algebra.syntax._

/**
 * Backstitch optimizer.
 *
 * Reference:
 * <p>
 * Y Wang, V Peddinti, H Xu, X Zhang, D Povey, S Khudanpur (2017):
 * Backstitch: Counteracting Finite-sample Bias via Negative Steps. INTERSPEECH.
 * [[http://www.isca-speech.org/archive/Interspeech_2017/pdfs/1323.PDF]]
 * </p>
 *
 * @param ν Learning rate
 * @param α Relative size of negative step
 * @param n One negative step every ''n'' steps
 * @author Tongfei Chen
 */
class BackstitchOptimizer(ν: Double, α: Double = 0.3, n: Int = 2) extends FirstOrderOptimizer {

  def updateParam[X](p: Param[X], g: X) = {
    implicit val ops = p.tag

    if (t % n == 0) { // negative step!
      p += g :* ((n - 1) * α * ν)
    }
    else {
      p -= g :* ((1 + α) * ν)
    }
  }

}
