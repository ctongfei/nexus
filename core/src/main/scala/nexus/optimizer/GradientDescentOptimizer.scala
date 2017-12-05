package nexus.optimizer

import nexus._
import nexus.op._
import nexus.algebra.syntax._

/**
 * Basic stochastic gradient descent optimizer.
 * @param η Learning rate
 * @author Tongfei Chen
 * @since 0.1.0
 */
class GradientDescentOptimizer(η: => Double) extends FirstOrderOptimizer {

  def updateParam[X](p: Param[X], g: X) = {
    implicit val tag = p.tag
    p -= g :* η
  }

}
