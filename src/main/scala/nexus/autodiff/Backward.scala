package nexus.autodiff

import nexus._
import shapeless._

/**
 * @author Tongfei Chen
 */
object Backward {

  def compute[D, T[D, A <: HList]](e: Expr[T[D, $]], values: ValueStore)(implicit env: Env[T, D]): ValueStore = {
    import env._
    val gradients = ValueStore(e -> scalar(env.one)) // gradient of loss is 1

    def update(e: GenExpr, v: Any) = {
      if (gradients contains e)
        gradients(e) = addU(untype(gradients(e).asInstanceOf[T[D, _]]), untype(v.asInstanceOf[T[D, _]]))
      else
        gradients(e) = v
    }

    def eval[Y](e: Expr[Y]): Unit = e match {
      case Input(_) =>
      case Param(p, _) =>
      case Const(_, _) =>
      case Apply1(o: Op1[eX, Y], x) =>
        val g = untype(o.backward(gradients(e).asInstanceOf[Y], values(e).asInstanceOf[Y], values(x).asInstanceOf[eX]).asInstanceOf[T[D, _]])
        update(x, g)
        eval(x)
      case Apply2(o: Op2[eX1, eX2, Y], x1, x2) =>
        val g1 = untype(o.backward1(gradients(e).asInstanceOf[Y], values(e).asInstanceOf[Y], values(x1).asInstanceOf[eX1], values(x2).asInstanceOf[eX2]).asInstanceOf[T[D, _]])
        val g2 = untype(o.backward2(gradients(e).asInstanceOf[Y], values(e).asInstanceOf[Y], values(x1).asInstanceOf[eX1], values(x2).asInstanceOf[eX2]).asInstanceOf[T[D, _]])
        update(x1, g1)
        update(x2, g2)
        eval(x1)
        eval(x2)
    }

    eval(e)
    gradients
  }

}
