package nexus.exec

import cats._
import nexus._

/**
 * Performs forward computation on a computation graph.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Forward {

  /**
   * An instance of a forward computation.
   * @param values Cache for computed values of expressions in this instance
   */
  class Instance(val values: ExprValueMap) extends (Expr ~> Id) {
    def apply[A](e: Expr[A]) = {
      if (values contains e) values(e)
      else e match {
        case Input(_) =>
          val x = values(e)
          values(e) = x; x
        case Param(x, _) => values(e) = x; x
        case Const(x, _) => values(e) = x; x
        case Apply1(o, x) =>
          val y = o.forward(apply(x))
          values(e) = y; y
        case Apply2(o, x1, x2) =>
          val y = o.forward(apply(x1), apply(x2))
          values(e) = y; y
        case Apply3(o, x1, x2, x3) =>
          val y = o.forward(apply(x1), apply(x2), apply(x3))
          values(e) = y; y
      }
    }
  }

  def given(values: ExprValueMap): Expr ~> Id = new Instance(values)

  def compute[X](e: Expr[X])(inputs: Assignment*): (X, ExprValueMap) =
    compute(e, ExprValueMap(inputs: _*))

  def compute[X](e: Expr[X], inputs: ExprValueMap): (X, ExprValueMap) = {
    val eval = given(inputs)
    val y = eval(e)
    (y, inputs)
  }

}

