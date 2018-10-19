package nexus.syntax

import nexus._

/**
 * @author Tongfei Chen
 */
trait TupleExprOpsMixin {

  implicit class Tuple2ExprOps[X1, X2](val p: (Symbolic[X1], Symbolic[X2])) {

    def |>[Y]
    (f: Func2[X1, X2, Y]): Symbolic[Y] =
      f(p._1, p._2)

    def |>[Y]
    (f: PolyFunc2)
    (implicit ff: f.F[X1, X2, Y]): Symbolic[Y] =
      f(p._1, p._2)

  }


  implicit class Tuple3ExprOps[X1, X2, X3](val t: (Symbolic[X1], Symbolic[X2], Symbolic[X3])) {

    def |>[Y]
    (f: Func3[X1, X2, X3, Y]): Symbolic[Y] =
      f(t._1, t._2, t._3)

    def |>[Y]
    (f: PolyFunc3)
    (implicit ff: f.F[X1, X2, X3, Y]): Symbolic[Y] =
      f(t._1, t._2, t._3)

  }

}
