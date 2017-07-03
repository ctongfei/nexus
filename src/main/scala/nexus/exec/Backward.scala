package nexus.exec

import nexus._
import shapeless._

/**
 * Performs backward computation (backpropagation through the neural network).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Backward {

  def compute[D, T[D, A <: HList]](e: Expr[T[D, $]], values: Values[T, D])(implicit env: Env[T, D]): Values[T, D] = {
    import env._
    val gradients = Values(e ->> typeWith(scalar(env.one), $)) // gradient of loss is 1

    def eval[Y](e: Expr[Y]): Unit = e match {
      case Input(_) =>
      case Param(p, _) =>
      case Const(_, _) =>
      case Apply1(o: Op1[eX, Y], x) => if (x.computeGradient) {
        val g = untype(o.backward(gradients(e).asInstanceOf[Y], values(e).asInstanceOf[Y], values(x).asInstanceOf[eX]).asInstanceOf[T[D, _]])
        gradients.increment(x, g)
        eval(x)
      }
      case Apply2(o: Op2[eX1, eX2, Y], x1, x2) =>
        if (x1.computeGradient) {
          val g1 = untype(o.backward1(gradients(e).asInstanceOf[Y], values(e).asInstanceOf[Y], values(x1).asInstanceOf[eX1], values(x2).asInstanceOf[eX2]).asInstanceOf[T[D, _]])
          gradients.increment(x1, g1)
          eval(x1)
        }
        if (x2.computeGradient) {
          val g2 = untype(o.backward2(gradients(e).asInstanceOf[Y], values(e).asInstanceOf[Y], values(x1).asInstanceOf[eX1], values(x2).asInstanceOf[eX2]).asInstanceOf[T[D, _]])
          gradients.increment(x2, g2)
          eval(x2)
        }
    }

    eval(e)
    gradients
  }

}
