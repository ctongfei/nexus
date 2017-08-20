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

  def compute[T[A <: HList], D](e: Expr[T[$]], values: ExprValueMap)(implicit ops: TypedRealTensorOps[T, D]): ExprValueMap = {

    val ∇ = ExprValueMap(e <<- ops.wrapScalar(ops.D.one)) // gradient of loss is 1

    def eval[Y](e: Expr[Y]): Unit = e match {

      case DApply1(o: DOp1[eX, Y], x: DExpr[_]) =>
          val g = o.backward(∇(e), values(e), values(x).asInstanceOf[eX]).asInstanceOf[T[_]]
          ∇.increment(x, g)
          eval(x)

      case DApply2(o: DOp2[eX1, eX2, Y], x1, x2) =>
        if (x1.isInstanceOf[DExpr[eX1]]) {
          val g1 = o.backward1(∇(e), values(e), values(x1).asInstanceOf[eX1], values(x2).asInstanceOf[eX2]).asInstanceOf[T[_]]
          ∇.increment(x1.asInstanceOf[DExpr[eX1]], g1)
          eval(x1)
        }
        if (x2.isInstanceOf[DExpr[eX2]]) {
          val g2 = o.backward2(∇(e), values(e), values(x1).asInstanceOf[eX1], values(x2).asInstanceOf[eX2]).asInstanceOf[T[_]]
          ∇.increment(x2.asInstanceOf[DExpr[eX2]], g2)
          eval(x2)
        }

      case DApply3(o: DOp3[eX1, eX2, eX3, Y], x1, x2, x3) =>
        if (x1.isInstanceOf[DExpr[eX1]]) {
          val g1 = o.backward1(∇(e), values(e), values(x1), values(x2), values(x3)).asInstanceOf[T[_]]
          ∇.increment(x1.asInstanceOf[DExpr[eX1]], g1)
          eval(x1)
        }
        if (x2.isInstanceOf[DExpr[eX2]]) {
          val g2 = o.backward2(∇(e), values(e), values(x1), values(x2), values(x3)).asInstanceOf[T[_]]
          ∇.increment(x2.asInstanceOf[DExpr[eX2]], g2)
          eval(x2)
        }
        if (x3.isInstanceOf[DExpr[eX3]]) {
          val g3 = o.backward3(∇(e), values(e), values(x1), values(x2), values(x3)).asInstanceOf[T[_]]
          ∇.increment(x3.asInstanceOf[DExpr[eX3]], g3)
          eval(x3)
        }
      case _ => // not differentiable
    }

    eval(e)
    ∇
  }

}
