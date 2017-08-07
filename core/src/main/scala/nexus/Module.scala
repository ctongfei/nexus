package nexus

/**
 * @author Tongfei Chen
 */
trait Module[X, Y] extends (Expr[X] => Expr[Y])

trait Module2[X1, X2, Y] extends ((Expr[X1], Expr[X2]) => Expr[Y])

trait Module3[X1, X2, X3, Y] extends ((Expr[X1], Expr[X2], Expr[X3]) => Expr[Y])
