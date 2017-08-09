package nexus

/**
 * @author Tongfei Chen
 */
trait Tuple2ExprOpsMixin {

  implicit class Tuple2ExprOps[X1, X2](val p: (Expr[X1], Expr[X2])) {

    def |>[Y]
    (f: Module2[X1, X2, Y]): Expr[Y] =
      f(p._1, p._2)

    def |>[F[X1, X2, Y] <: Op2[X1, X2, Y], Y]
    (op: PolyOp2[F])
    (implicit f: F[X1, X2, Y]): Expr[Y] =
      f(p._1, p._2)

    def |>[F[P, X1, X2, Y] <: (P => Op2[X1, X2, Y]), P, Y]
    (op: ParaPolyOp2[P, F])
    (implicit f: F[P, X1, X2, Y]): Expr[Y] =
      f(op.parameter)(p._1, p._2)

  }

}