package nexus

/**
 * Polymorphic unary fallbackable operator (could be either differentiable or non-differentiable).
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait PolyFOp1 extends PolyOp1 {

  type DOp[X, Y] <: DOp1[X, Y]

  def apply[X, Y](x: DExpr[X])(implicit f: DOp[X, Y]): DExpr[Y] = DApply1(f, x)

}

trait PolyFOp2 extends PolyOp2 {

  type DOp[X1, X2, Y] <: DOp2[X1, X2, Y]

  def apply[X1, X2, Y](x1: DExpr[X1], x2:  Expr[X2])(implicit f: DOp[X1, X2, Y]): DExpr[Y] = DApply2(f, x1, x2)
  def apply[X1, X2, Y](x1:  Expr[X1], x2: DExpr[X2])(implicit f: DOp[X1, X2, Y]): DExpr[Y] = DApply2(f, x1, x2)
  def apply[X1, X2, Y](x1: DExpr[X1], x2: DExpr[X2])(implicit f: DOp[X1, X2, Y]): DExpr[Y] = DApply2(f, x1, x2)

}


trait PolyFOp3 extends PolyOp3 {

  type DOp[X1, X2, X3, Y] <: DOp3[X1, X2, X3, Y]

  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2:  Expr[X2], x3:  Expr[X3])(implicit f: DOp[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)
  def apply[X1, X2, X3, Y](x1:  Expr[X1], x2: DExpr[X2], x3:  Expr[X3])(implicit f: DOp[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)
  def apply[X1, X2, X3, Y](x1:  Expr[X1], x2:  Expr[X2], x3: DExpr[X3])(implicit f: DOp[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: DExpr[X2], x3:  Expr[X3])(implicit f: DOp[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2:  Expr[X2], x3: DExpr[X3])(implicit f: DOp[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)
  def apply[X1, X2, X3, Y](x1:  Expr[X1], x2: DExpr[X2], x3: DExpr[X3])(implicit f: DOp[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: DExpr[X2], x3: DExpr[X3])(implicit f: DOp[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)

}
