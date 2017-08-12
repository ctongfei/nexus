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
class StochasticGradientDescentOptimizer[T[_ <: $$], D] private(val η: D)(implicit val env: Env[T, D]) extends Optimizer[T, D] {
  import env._

  def update(gradients: Values[T, D]) = {
    for ((p, grad) <- gradients.map) {
      p match {
        case Param(value, _) =>
          val v = untype(value.asInstanceOf[T[_]])
          val g = untype(grad.asInstanceOf[T[_]])
          addInplace(v, scaleU(g, negS(η)))
        case _ =>
      }
    }
  }

}

object StochasticGradientDescentOptimizer {

  def apply[T[_ <: $$], D](η: D)(implicit env: Env[T, D]) = new StochasticGradientDescentOptimizer(η)

}
