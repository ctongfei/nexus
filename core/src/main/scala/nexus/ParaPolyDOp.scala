package nexus

/**
 * @author Tongfei Chen
 */
trait ParaPolyDOp1[P, F[P, X, Y] <: (P => DOp1[X, Y])] extends ParaPolyOp1[P, F] { self =>

  /**
   * Performs gradient backpropagation.
   * @param dy Gradient of ''y'': ∇y
   * @param y Value of ''y''
   * @param x Value of ''x''
   * @return Gradient of ''x'': ∇x
   */
  def backward[X, Y](dy: Y, y: Y, x: X)(implicit f: F[P, X, Y]): X =
    f(parameter).backward(dy, y, x)

  override def ground[X, Y](implicit f: F[P, X, Y]): DOp1[X, Y] =
    f(parameter)

  def apply[X, Y](x: DExpr[X])(implicit f: F[P, X, Y]) = DApply1(f(parameter), x)

}

trait ParaPolyDOp2[P, F[P, X1, X2, Y] <: (P => DOp2[X1, X2, Y])] extends ParaPolyOp2[P, F] { self =>

  def backward1[X1, X2, Y](dy: Y, y: Y, x1: X1, x2: X2)(implicit f: F[P, X1, X2, Y]): X1 =
    f(parameter).backward1(dy, y, x1, x2)

  def backward2[X1, X2, Y](dy: Y, y: Y, x1: X1, x2: X2)(implicit f: F[P, X1, X2, Y]): X2 =
    f(parameter).backward2(dy, y, x1, x2)


  override def ground[X1, X2, Y](implicit f: F[P, X1, X2, Y]): Op2[X1, X2, Y] =
    f(parameter)


  def apply[X1, X2, Y](x1: DExpr[X1], x2: Expr[X2])(implicit f: F[P, X1, X2, Y]) = DApply2(f(parameter), x1, x2)
  def apply[X1, X2, Y](x1: Expr[X1], x2: DExpr[X2])(implicit f: F[P, X1, X2, Y]) = DApply2(f(parameter), x1, x2)
  def apply[X1, X2, Y](x1: DExpr[X1], x2: DExpr[X2])(implicit f: F[P, X1, X2, Y]) = DApply2(f(parameter), x1, x2)

}

trait ParaPolyDOp3[P, F[P, X1, X2, X3, Y] <: (P => DOp3[X1, X2, X3, Y])] extends ParaPolyOp3[P, F] { self =>

  def backward1[X1, X2, X3, Y](dy: Y, y: Y, x1: X1, x2: X2, x3: X3)(implicit f: F[P, X1, X2, X3, Y]): X1 =
    f(parameter).backward1(dy, y, x1, x2, x3)

  def backward2[X1, X2, X3, Y](dy: Y, y: Y, x1: X1, x2: X2, x3: X3)(implicit f: F[P, X1, X2, X3, Y]): X2 =
    f(parameter).backward2(dy, y, x1, x2, x3)

  def backward3[X1, X2, X3, Y](dy: Y, y: Y, x1: X1, x2: X2, x3: X3)(implicit f: F[P, X1, X2, X3, Y]): X3 =
    f(parameter).backward3(dy, y, x1, x2, x3)

  override def ground[X1, X2, X3, Y](implicit f: F[P, X1, X2, X3, Y]): DOp3[X1, X2, X3, Y] =
    f(parameter)


  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: Expr[X2], x3: Expr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: DExpr[X2], x3: Expr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: Expr[X2], x3: DExpr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: DExpr[X2], x3: Expr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: Expr[X2], x3: DExpr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: DExpr[X2], x3: DExpr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: DExpr[X2], x3: DExpr[X3])(implicit f: F[P, X1, X2, X3, Y]) = DApply3(f(parameter), x1, x2, x3)


}