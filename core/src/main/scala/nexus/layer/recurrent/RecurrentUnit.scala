package nexus.layer.recurrent

import nexus._

/**
 * @author Tongfei Chen
 */
trait RecurrentUnit[S, I] extends ((Expr[S], Expr[I]) => Expr[S])
