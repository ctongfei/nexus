package nexus.exec

import nexus._
import nexus.impl._
import shapeless._

/**
 * Performs backward computation (backpropagation through the neural network).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Backward {

  def compute[T[A <: HList], D](e: Expr[T[$]], values: Values)(implicit ops: TypedMathOps[T, D]): Values = {

    val ∇ = Values(e <<- ops.scalar(ops.D.one)) // gradient of loss is 1

    def eval[Y](e: Expr[Y]): Unit = e match {
      case Input(_) =>
      case Param(_, _) =>
      case Const(_, _) =>
      case Apply1(o: Op1[eX, Y], x) =>
          val g = o.backward(∇(e), values(e), values(x).asInstanceOf[eX]).asInstanceOf[T[_]]
          ∇.increment(x, g)
          eval(x)
      case Apply2(o: Op2[eX1, eX2, Y], x1, x2) =>
          val g1 = o.backward1(∇(e), values(e), values(x1).asInstanceOf[eX1], values(x2).asInstanceOf[eX2]).asInstanceOf[T[_]]
          ∇.increment(x1, g1)
          eval(x1)
          val g2 = o.backward2(∇(e), values(e), values(x1).asInstanceOf[eX1], values(x2).asInstanceOf[eX2]).asInstanceOf[T[_]]
          ∇.increment(x2, g2)
          eval(x2)
      case Apply3(o: Op3[eX1, eX2, eX3, Y], x1, x2, x3) =>
          val g1 = o.backward1(∇(e), values(e), values(x1), values(x2), values(x3)).asInstanceOf[T[_]]
          ∇.increment(x1, g1)
          eval(x1)
          val g2 = o.backward2(∇(e), values(e), values(x1), values(x2), values(x3)).asInstanceOf[T[_]]
          ∇.increment(x2, g2)
          eval(x2)
          val g3 = o.backward3(∇(e), values(e), values(x1), values(x2), values(x3)).asInstanceOf[T[_]]
          ∇.increment(x3, g3)
          eval(x3)
      case _ => // not differentiable
    }

    eval(e)
    ∇
  }

}
