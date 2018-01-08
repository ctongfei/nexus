package nexus.optimizer

import nexus._
import nexus.exec._

/**
 * Base abstract class for optimizers.
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class OneStepFirstOrderOptimizer {

  protected var t = 0

  def iteration = t

  def updateParam[X](p: Param[X], g: X): Unit

  def update(gradients: ExprValueMap): Unit = {
    t += 1
    for (item <- gradients) {
      item.expr match {
        case param: Param[item.Data] =>
          updateParam(param, item.value)
        case _ =>
      }
    }
  }

}
