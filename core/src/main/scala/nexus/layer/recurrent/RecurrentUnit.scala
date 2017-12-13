package nexus.layer.recurrent

import nexus._

/**
 * @author Tongfei Chen
 */
trait RecurrentUnit[S, X] extends ((Expr[S], Expr[X]) => Expr[S])

trait RecurrentUnitWithOutput[S, X, Y] extends ((Expr[S], Expr[X]) => (Expr[S], Expr[Y]))
