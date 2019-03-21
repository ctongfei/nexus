package nexus.diff.execution

import cats.~>
import nexus.diff._
import nexus._
import nexus.diff.collection._

/**
 * Performs backward computation (backpropagation through the neural network).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class SymbolicBackward(val forward: SymbolicForward) extends Backward[Symbolic] {

  def compute[R](e: Symbolic[R])(implicit R: IsReal[R]): BoxMap[Symbolic, Id] = {

    val grad = WengertList(Assignment(e, R.one)) // gradient of loss is 1

    for (a <- forward.values.reverse) a match {
      case a @ Assignment(e, v) => e match {
        case e @ Symbolic.App1(o, x) => o match {
          case o: Op1[e.Input, _] if x.requireGrad =>
            val g = o.backward(grad(e), v, forward(x))
            grad.increment(x, g)
          case _ => // not differentiable
        }
        case e @ Symbolic.App2(o, x1, x2) => o match {
          case o: Op2[e.Input1, e.Input2, _] =>
            if (x1.requireGrad) {
              val g1 = o.backward1(grad(e), v, forward(x1), forward(x2))
              grad.increment(x1, g1)
            }
            if (x2.requireGrad) {
              val g2 = o.backward2(grad(e), v, forward(x1), forward(x2))
              grad.increment(x2, g2)
            }
          case _ =>
        }
        case e @ Symbolic.App3(o, x1, x2, x3) => o match {
          case o: Op3[e.Input1, e.Input2, e.Input3, _] =>
            if (x1.requireGrad) {
              val g1 = o.backward1(grad(e), v, forward(x1), forward(x2), forward(x3))
              grad.increment(x1, g1)
            }
            if (x2.requireGrad) {
              val g2 = o.backward2(grad(e), v, forward(x1), forward(x2), forward(x3))
              grad.increment(x2, g2)
            }
            if (x3.requireGrad) {
              val g3 = o.backward3(grad(e), v, forward(x1), forward(x2), forward(x3))
              grad.increment(x3, g3)
            }
        }
        case _ => // not differentiable
      }
    }
    grad
  }

}
