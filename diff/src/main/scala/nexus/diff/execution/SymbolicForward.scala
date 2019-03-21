package nexus.diff.execution

import nexus.diff._
import nexus.diff.collection._
import nexus.diff.exception._

/**
 * Interprets a computation graph naively -- without doing any automatic batching.
 * @param values Cache for computed values of expressions in this instance
 * @author Tongfei Chen
 * @since 0.1.0
 */
class SymbolicForward private(val values: WengertList[Symbolic]) extends Forward[Symbolic] {

  def apply[A](e: Symbolic[A]): A = {
    if (values contains e) values(e)
    else e match {

      case Param(x, _) => values(e) = x; x
      case Const(x, _) => values(e) = x; x

      case Symbolic.App1(o, x) =>
        val y = o.forward(apply(x))
        values(e) = y; y
      case Symbolic.App2(o, x1, x2) =>
        val y = o.forward(apply(x1), apply(x2))
        values(e) = y; y
      case Symbolic.App3(o, x1, x2, x3) =>
        val y = o.forward(apply(x1), apply(x2), apply(x3))
        values(e) = y; y

      case e: Input[A] =>
        throw new InputNotGivenException(e) // should already be in `values`
    }
  }

  def backward = new SymbolicBackward(this)
}

object SymbolicForward extends ForwardFactory[Symbolic, SymbolicForward] {

  def given(inputs: Assignment[Symbolic]*): SymbolicForward = new SymbolicForward(WengertList(inputs: _*))

  def compute[X](e: Symbolic[X])(inputs: Assignment[Symbolic]*): (X, SymbolicForward) =
    compute(e, WengertList(inputs: _*))

  def compute[X](e: Symbolic[X], inputs: WengertList[Symbolic]): (X, SymbolicForward) = {
    val eval = new SymbolicForward(inputs)
    val y = eval(e)
    (y, eval)
  }

}
