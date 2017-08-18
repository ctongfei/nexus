package nexus

/**
 * @author Tongfei Chen
 */
trait Module[X, Y] extends (Expr[X] => Expr[Y])

trait Module2[X1, X2, Y] extends ((Expr[X1], Expr[X2]) => Expr[Y])

trait Module3[X1, X2, X3, Y] extends ((Expr[X1], Expr[X2], Expr[X3]) => Expr[Y])

trait DModule[X, Y] extends Module[X, Y] with (Expr[X] => DExpr[Y])

trait DModule2[X1, X2, Y] extends Module2[X1, X2, Y] with ((Expr[X1], Expr[X2]) => DExpr[Y])

trait DModule3[X1, X2, X3, Y] extends Module3[X1, X2, X3, Y] with ((Expr[X1], Expr[X2], Expr[X3]) => DExpr[Y])
