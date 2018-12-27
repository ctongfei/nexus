package nexus.diff.optimizers

import nexus.diff._
import nexus.syntax._

/**
 * Basic stochastic gradient descent optimizer.
 * @param η Learning rate
 * @author Tongfei Chen
 * @since 0.1.0
 */
class GradientDescentOptimizer(η: => Double) extends FirstOrderOptimizer {

  def updateParam[X](p: Param[X], g: X) = {
    implicit val ev = p.tag.ev
    p -= g :* η
  }

  def parameterMap = Map()  // No parameters

  def loadFromParameterMap(m: Map[String, Param[_]]): Unit = ()  // Do nothing

}
