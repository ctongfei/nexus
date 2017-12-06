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

  private def reverseTopologicalSort(e: Expr[_]): Iterable[Expr[_]] = {
    ???
  }

  def compute[R](e: Expr[R], values: ExprValueMap)(implicit R: IsReal[R]): ExprValueMap = {

    val ∇ = ExprValueMap(e <<- R.one) // gradient of loss is 1

    //TODO: Is DFS here correct? change to queue?
    def eval[Y](e: Expr[Y]): Unit = e match {

      case e @ Apply1(o, x) => o match {
        case o: DOp1[e.Input, Y] if x.requireGrad =>
          val g = o.backward(∇(e), values(e), values(x))
          ∇.increment(x, g)
          eval(x)
        case _ => // not differentiable
      }

      case e @ Apply2(o, x1, x2) => o match {
        case o: DOp2[e.Input1, e.Input2, Y] =>
          if (x1.requireGrad) {
            val g1 = o.backward1(∇(e), values(e), values(x1), values(x2))
            ∇.increment(x1, g1)
            eval(x1)
          }
          if (x2.requireGrad) {
            val g2 = o.backward2(∇(e), values(e), values(x1), values(x2))
            ∇.increment(x2, g2)
            eval(x2)
          }
        case _ =>
      }

      case e @ Apply3(o, x1, x2, x3) => o match {
        case o: DOp3[e.Input1, e.Input2, e.Input3, Y] =>
          if (x1.requireGrad) {
            val g1 = o.backward1(∇(e), values(e), values(x1), values(x2), values(x3))
            ∇.increment(x1, g1)
            eval(x1)
          }
          if (x2.requireGrad) {
            val g2 = o.backward2(∇(e), values(e), values(x1), values(x2), values(x3))
            ∇.increment(x2, g2)
            eval(x2)
          }
          if (x3.requireGrad) {
            val g3 = o.backward3(∇(e), values(e), values(x1), values(x2), values(x3))
            ∇.increment(x3, g3)
            eval(x3)
          }
      }
      case _ => // not differentiable
    }

    eval(e)
    ∇
  }

}
