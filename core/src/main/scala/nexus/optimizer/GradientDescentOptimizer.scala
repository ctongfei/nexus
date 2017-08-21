package nexus.optimizer

import nexus._
import nexus.exec._
import shapeless._

/**
 * Basic stochastic gradient descent optimizer.
 * @param η Learning rate
 * @author Tongfei Chen
 * @since 0.1.0
 */
class GradientDescentOptimizer(val η: Double) extends Optimizer {

  def updateParam[X](p: Param[X], g: X) = {
    implicit val ops = p.gradOps

    p -= g :* η

  }

}
