package nexus.execution

import cats._
import nexus._
import nexus.exception._

/**
 * Interprets a computation graph naively -- without doing any automatic batching.
 * @param values Cache for computed values of expressions in this instance
 * @author Tongfei Chen
 * @since 0.1.0
 */
class SimpleForward private(val values: WengertList) extends ForwardInterpreter {

  def apply[A](e: Expr[A]): A = {
    if (values contains e) values(e)
    else e match {

      case Param(x, _) => values(e) = x; x
      case Const(x, _) => values(e) = x; x

      case App1(o, x) =>
        val y = o.forward(apply(x))
        values(e) = y; y
      case App2(o, x1, x2) =>
        val y = o.forward(apply(x1), apply(x2))
        values(e) = y; y
      case App3(o, x1, x2, x3) =>
        val y = o.forward(apply(x1), apply(x2), apply(x3))
        values(e) = y; y

      case e @ Input(_) => throw new InputNotGivenException(e) // should already be in `values`
    }
  }

}

object SimpleForward extends ForwardInterpreterFactory[SimpleForward] {

  def given(inputs: Assignment*): SimpleForward = new SimpleForward(WengertList(inputs: _*))

  def compute[X](e: Expr[X])(inputs: Assignment*): (X, WengertList) =
    compute(e, WengertList(inputs: _*))

  def compute[X](e: Expr[X], inputs: WengertList): (X, WengertList) = {
    val eval = new SimpleForward(inputs)
    val y = eval(e)
    (y, inputs)
  }

}
