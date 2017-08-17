package nexus.exec

import nexus._
import shapeless._

/**
 * Performs forward computation on a computation graph.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Forward {

  def compute[T[_ <: $$], D, X](e: Expr[X])(inputs: Assignment*): (X, Values) =
    compute(e, Values(inputs: _*))

  def compute[T[_ <: $$], D, X](e: Expr[X], inputs: Values): (X, Values) = {
    val values = inputs

    def eval[X](e: Expr[X]): X = {
      if (values contains e) values(e)
      else e match {
        case Input(_) =>
          val x = inputs(e)
          values(e) = x; x
        case Param(x, _) => values(e) = x; x
        case Const(x, _) => values(e) = x; x
        case Apply1(o, x) =>
          val y = o.forward(eval(x))
          values(e) = y; y
        case Apply2(o, x1, x2) =>
          val y = o.forward(eval(x1), eval(x2))
          values(e) = y; y
        case Apply3(o, x1, x2, x3) =>
          val y = o.forward(eval(x1), eval(x2), eval(x3))
          values(e) = y; y
      }
    }

    val y = eval(e)
    (y, values)
  }

}

