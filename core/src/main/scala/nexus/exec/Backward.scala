package nexus.exec

import nexus._
import nexus.algebra._
import shapeless._

/**
 * Performs backward computation (backpropagation through the neural network).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Backward {

  def compute[R](e: Expr[R], values: ExprValueMap)(implicit R: RealOps[R]): ExprValueMap = {

    val ∇ = ExprValueMap(e <<- R.one) // gradient of loss is 1

    def eval[Y](e: Expr[Y]): Unit = e match {

      case e @ DApply1(o, x) => {
        x match {
          case x: DExpr[e.Input] =>
            val g = o.backward(∇(e), values(e), values(x))
            ∇.increment(x, g)
            eval(x)
          case _ => // not differentiable
        }
      }

      case e @ DApply2(o, x1, x2) => {
        x1 match {
          case x1: DExpr[e.Input1] =>
            val g1 = o.backward1(∇(e), values(e), values(x1), values(x2))
            ∇.increment(x1, g1)
            eval(x1)
          case _ => // not differentiable
        }
        x2 match {
          case x2: DExpr[e.Input2] =>
            val g2 = o.backward2(∇(e), values(e), values(x1), values(x2))
            ∇.increment(x2, g2)
            eval(x2)
          case _ => // not differentiable
        }
      }

      case e @ DApply3(o, x1, x2, x3) =>
        x1 match {
          case x1: DExpr[e.Input1] =>
            val g1 = o.backward1(∇(e), values(e), values(x1), values(x2), values(x3))
            ∇.increment(x1, g1)
            eval(x1)
          case _ => // not differentiable
        }
        x2 match {
          case x2: DExpr[e.Input2] =>
            val g2 = o.backward2(∇(e), values(e), values(x1), values(x2), values(x3))
            ∇.increment(x2, g2)
            eval(x2)
          case _ => // not differentiable
        }
        x3 match {
          case x3: DExpr[e.Input3] =>
            val g3 = o.backward3(∇(e), values(e), values(x1), values(x2), values(x3))
            ∇.increment(x3, g3)
            eval(x3)
          case _ => // not differentiable
        }
      case _ => // not differentiable
    }

    eval(e)
    ∇
  }

}
