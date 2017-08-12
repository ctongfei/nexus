package nexus.exec

import nexus._
import shapeless._

/**
 * Performs backward computation (backpropagation through the neural network).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Backward {

  def compute[D, T[A <: HList]](e: Expr[T[$]], values: Values[T, D])(implicit env: Env[T, D]): Values[T, D] = {
    import env._
    val ∇ = Values(e <<- typeWith(scalar(one), $)) // gradient of loss is 1

    def eval[Y](e: Expr[Y]): Unit = e match {
      case Input(_) =>
      case Param(_, _) =>
      case Const(_, _) =>
      case Apply1(o: Op1[eX, Y], x) =>
        if (o.differentiableWrtX) {
          val g = untype(o.backward(∇(e), values(e), values(x).asInstanceOf[eX]).asInstanceOf[T[_]])
          ∇.increment(x, g)
          eval(x)
        }
      case Apply2(o: Op2[eX1, eX2, Y], x1, x2) =>
        if (o.differentiableWrtX1) {
          val g1 = untype(o.backward1(∇(e), values(e), values(x1).asInstanceOf[eX1], values(x2).asInstanceOf[eX2]).asInstanceOf[T[_]])
          ∇.increment(x1, g1)
          eval(x1)
        }
        if (o.differentiableWrtX2) {
          val g2 = untype(o.backward2(∇(e), values(e), values(x1).asInstanceOf[eX1], values(x2).asInstanceOf[eX2]).asInstanceOf[T[_]])
          ∇.increment(x2, g2)
          eval(x2)
        }
      case Apply3(o: Op3[eX1, eX2, eX3, Y], x1, x2, x3) =>
        if (o.differentiableWrtX1) {
          val g1 = untype(o.backward1(∇(e), values(e), values(x1), values(x2), values(x3)).asInstanceOf[T[_]])
          ∇.increment(x1, g1)
          eval(x1)
        }
        if (o.differentiableWrtX2) {
          val g2 = untype(o.backward2(∇(e), values(e), values(x1), values(x2), values(x3)).asInstanceOf[T[_]])
          ∇.increment(x2, g2)
          eval(x2)
        }
        if (o.differentiableWrtX3) {
          val g3 = untype(o.backward3(∇(e), values(e), values(x1), values(x2), values(x3)).asInstanceOf[T[_]])
          ∇.increment(x3, g3)
          eval(x3)
        }

    }

    eval(e)
    ∇
  }

}
