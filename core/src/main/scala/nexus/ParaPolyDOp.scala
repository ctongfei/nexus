package nexus

/**
 * @author Tongfei Chen
 */
trait ParaPolyDOp1[P, F[P, X, Y] <: (P => DOp1[X, Y])] extends ParaPolyOp1[P, F] { self =>

  def apply[X, Y](x: DExpr[X])(implicit f: F[P, X, Y]) = DApply1(f(parameter), x)

}

trait ParaPolyDOp2[P, F[P, X1, X2, Y] <: (P => DOp2[X1, X2, Y])] extends ParaPolyOp2[P, F] { self =>

  def apply[X1, X2, Y](x1: DExpr[X1], x2: Expr[X2])(implicit f: F[P, X1, X2, Y]) = DApply2(f(parameter), x1, x2)
  def apply[X1, X2, Y](x1: Expr[X1], x2: DExpr[X2])(implicit f: F[P, X1, X2, Y]) = DApply2(f(parameter), x1, x2)
  def apply[X1, X2, Y](x1: DExpr[X1], x2: DExpr[X2])(implicit f: F[P, X1, X2, Y]) = DApply2(f(parameter), x1, x2)

}

trait ParaPolyDOp3[P, F[P, X1, X2, X3, Y] <: (P => DOp3[X1, X2, X3, Y])] extends ParaPolyOp3[P, F] { self =>

  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: Expr[X2], x3: Expr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: DExpr[X2], x3: Expr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: Expr[X2], x3: DExpr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: DExpr[X2], x3: Expr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: Expr[X2], x3: DExpr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: DExpr[X2], x3: DExpr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: DExpr[X2], x3: DExpr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)

}
