package nexus.syntax

import nexus._

/**
 * @author Tongfei Chen
 */
trait TupleExprOpsMixin {

  implicit class Tuple2ExprOps[X1, X2](val p: (Expr[X1], Expr[X2])) {

    def |>[Y]
    (f: Module2[X1, X2, Y]): Expr[Y] =
      f(p._1, p._2)

    def |>[Y]
    (op: PolyOp2)
    (implicit f: op.F[X1, X2, Y]): Expr[Y] =
      f(p._1, p._2)


    def |>[Y]
    (op: PolyDOp2)
    (implicit f: op.F[X1, X2, Y]): Expr[Y] =
      f(p._1, p._2)

    def |>[P, Y]
    (op: ParaPolyOp2[P])
    (implicit f: op.F[P, X1, X2, Y]): Expr[Y] =
      f(op.parameter)(p._1, p._2)

    def |>[P, Y]
    (op: ParaPolyDOp2[P])
    (implicit f: op.F[P, X1, X2, Y]): Expr[Y] =
      f(op.parameter)(p._1, p._2)

  }


  implicit class Tuple3ExprOps[X1, X2, X3](val t: (Expr[X1], Expr[X2], Expr[X3])) {

    def |>[Y]
    (f: Module3[X1, X2, X3, Y]): Expr[Y] =
      f(t._1, t._2, t._3)

    def |>[Y]
    (op: PolyOp3)
    (implicit f: op.F[X1, X2, X3, Y]): Expr[Y] =
      f(t._1, t._2, t._3)

    def |>[P, Y]
    (op: ParaPolyOp3[P])
    (implicit f: op.F[P, X1, X2, X3, Y]): Expr[Y] =
      f(op.parameter)(t._1, t._2, t._3)

  }

}