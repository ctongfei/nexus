package nexus.optimizer

import nexus._
import nexus.exec._

/**
 * Base trait for optimizers.
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class Optimizer {

  private var _t = 0

  def t = _t

  def updateParam[X](p: Param[X], g: X): Unit

  def update(gradients: ExprValueMap): Unit = {
    _t += 1
    for (item <- gradients) {
      item.expr match {
        case p: Param[item.Data] =>
          updateParam(p, item.value)
        case _ =>
      }
    }
  }

}
