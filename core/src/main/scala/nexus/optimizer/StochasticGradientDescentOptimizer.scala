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
class StochasticGradientDescentOptimizer private(val η: Double) extends Optimizer {

  def update(gradients: Values) = {
    for ((p @ Param(value, _), grad) <- gradients.map) {
      import p.ops._
      addI(value, scale(grad, -η))
    }
  }

}

object StochasticGradientDescentOptimizer {

  def apply[T[_ <: $$], D](η: Double) = new StochasticGradientDescentOptimizer(η)

}
