package nexus.optimizer

import nexus._
import nexus.algebra._
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

  def update(gradients: WengertList): Unit = {
    t += 1
    for (item <- gradients) {
      item.expr match {
        case param: Param[item.Data] =>
          updateParam(param, item.value)
        case _ =>
      }
    }
  }

  def step[R: IsReal](loss: Expr[R])(implicit comp: Forward) = {
    val lossValue = loss.value
    val gradients = Backward.compute(loss)
    update(gradients)
  }

}
