package nexus.diff.optimizers

import cats.~>
import nexus._
import nexus.diff._
import nexus.diff.collection._
import nexus.diff.execution._
import nexus.diff.syntax._

/**
 * Base abstract class for optimizers.
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class FirstOrderOptimizer extends HasParameters {

  protected var t = 0

  def iteration = t

  def updateParam[X](p: Param[X], g: X): Unit

  def update[D[_]](gradients: BoxMap[D, Id])(implicit D: Algebra[D]): Unit = {
    t += 1
    for (item <- gradients) {
      item.expr match {
        case D.Param(param: Param[item.Data]) =>
          updateParam(param, item.value)
        case _ =>
      }
    }
  }

  def step[D[_]: DifferentiableAlgebra, R: IsReal](loss: D[R])(implicit forward: Forward[D]) = {
    val lossValue = loss.value
    val backward = forward.backward
    val gradients = backward.compute(loss)
    update(gradients)
  }

}
