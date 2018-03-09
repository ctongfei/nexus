package nexus.execution

import cats._
import nexus._
import nexus.algebra._

/**
 * Performs backward computation (backpropagation through the neural network).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Backward {

  def compute[R](e: Expr[R])(implicit R: IsReal[R], forward: SimpleForward): ExprMap[Id] = {

    val ∇ = WengertList(e := R.one) // gradient of loss is 1
    val values = forward.values

    for (a <- values.reverse) a match {
      case a @ Assignment(e, v) => e match {
        case e @ App1(o, x) => o match {
          case o: Op1[e.Input, _] if x.requireGrad =>
            val g = o.backward(∇(e), v, values(x))
            ∇.increment(x, g)
          case _ => // not differentiable
        }
        case e @ App2(o, x1, x2) => o match {
          case o: Op2[e.Input1, e.Input2, _] =>
            if (x1.requireGrad) {
              val g1 = o.backward1(∇(e), v, values(x1), values(x2))
              ∇.increment(x1, g1)
            }
            if (x2.requireGrad) {
              val g2 = o.backward2(∇(e), v, values(x1), values(x2))
              ∇.increment(x2, g2)
            }
          case _ =>
        }
        case e @ App3(o, x1, x2, x3) => o match {
          case o: Op3[e.Input1, e.Input2, e.Input3, _] =>
            if (x1.requireGrad) {
              val g1 = o.backward1(∇(e), v, values(x1), values(x2), values(x3))
              ∇.increment(x1, g1)
            }
            if (x2.requireGrad) {
              val g2 = o.backward2(∇(e), v, values(x1), values(x2), values(x3))
              ∇.increment(x2, g2)
            }
            if (x3.requireGrad) {
              val g3 = o.backward3(∇(e), v, values(x1), values(x2), values(x3))
              ∇.increment(x3, g3)
            }
        }
        case _ => // not differentiable
      }
    }
    ∇
  }

}
