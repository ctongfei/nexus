package nexus

/**
 * Polymorphic unary fallbackable operator (could be either differentiable or non-differentiable).
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait PolyFOp1[F[X, Y] <: DOp1[X, Y], NF[X, Y] <: Op1[X, Y]] extends PolyOp1[NF] {

  /**
   * Performs gradient backpropagation.
   * @param dy Gradient of ''y'': ∇y
   * @param y Value of ''y''
   * @param x Value of ''x''
   * @return Gradient of ''x'': ∇x
   */
  def backward[X, Y](dy: Y, y: Y, x: X)(implicit f: F[X, Y]): X = f.backward(dy, y, x)

  def apply[X, Y](x: DExpr[X])(implicit f: F[X, Y]): DExpr[Y] = DApply1(f, x)

}

trait PolyFOp2[F[X1, X2, Y] <: DOp2[X1, X2, Y], NF[X1, X2, Y] <: Op2[X1, X2, Y]] extends PolyOp2[NF] {
  def backward1[X1, X2, Y](dy: Y, y: Y, x1: X1, x2: X2)(implicit f: F[X1, X2, Y]): X1 = f.backward1(dy, y, x1, x2)
  def backward2[X1, X2, Y](dy: Y, y: Y, x1: X1, x2: X2)(implicit f: F[X1, X2, Y]): X2 = f.backward2(dy, y, x1, x2)

  def apply[X1, X2, Y](x1: DExpr[X1], x2: Expr[X2])(implicit f: F[X1, X2, Y]): DExpr[Y] = DApply2(f, x1, x2)
  def apply[X1, X2, Y](x1: Expr[X1], x2: DExpr[X2])(implicit f: F[X1, X2, Y]): DExpr[Y] = DApply2(f, x1, x2)
  def apply[X1, X2, Y](x1: DExpr[X1], x2: DExpr[X2])(implicit f: F[X1, X2, Y]): DExpr[Y] = DApply2(f, x1, x2)
}


trait PolyFOp3[F[X1, X2, X3, Y] <: DOp3[X1, X2, X3, Y], NF[X1, X2, X3, Y] <: Op3[X1, X2, X3, Y]] extends PolyOp3[NF] {
  def backward1[X1, X2, X3, Y](dy: Y, y: Y, x1: X1, x2: X2, x3: X3)(implicit f: F[X1, X2, X3, Y]): X1 = f.backward1(dy, y, x1, x2, x3)
  def backward2[X1, X2, X3, Y](dy: Y, y: Y, x1: X1, x2: X2, x3: X3)(implicit f: F[X1, X2, X3, Y]): X2 = f.backward2(dy, y, x1, x2, x3)
  def backward3[X1, X2, X3, Y](dy: Y, y: Y, x1: X1, x2: X2, x3: X3)(implicit f: F[X1, X2, X3, Y]): X3 = f.backward3(dy, y, x1, x2, x3)

  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: Expr[X2], x3: Expr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: DExpr[X2], x3: Expr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: Expr[X2], x3: DExpr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: DExpr[X2], x3: Expr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: Expr[X2], x3: DExpr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: DExpr[X2], x3: DExpr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)
  def apply[X1, X2, X3, Y](x1: DExpr[X1], x2: DExpr[X2], x3: DExpr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] = DApply3(f, x1, x2, x3)

}
