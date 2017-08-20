package nexus.optimizer

import nexus._
import nexus.exec._

/**
 * Base trait for optimizers.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Optimizer {

  def updateParam[X](p: Param[X], g: X): Unit

  def update(gradients: ExprValueMap): Unit = {
    for (item <- gradients) {
      item.expr match {
        case p: Param[item.Data] =>
          updateParam(p, item.value)
        case _ =>
      }
    }
  }

}
