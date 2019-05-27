package nexus.diff.syntax

import nexus.diff._

/**
 * @author Tongfei Chen
 */
trait TupleExprOpsMixin {

  implicit class Tuple2ExprOps[F[_], X1, X2](val p: (F[X1], F[X2]))(implicit F: Algebra[F]) {

    def |>[Y]
    (f: Func2[X1, X2, Y]): F[Y] =
      f(p._1, p._2)

    def |>[Y]
    (f: PolyFunc2)
    (implicit ff: f.P[X1, X2, Y]): F[Y] =
      f(p._1, p._2)

  }


  implicit class Tuple3ExprOps[F[_], X1, X2, X3](val t: (F[X1], F[X2], F[X3]))(implicit F: Algebra[F]) {

    def |>[Y]
    (f: Func3[X1, X2, X3, Y]): F[Y] =
      f(t._1, t._2, t._3)

    def |>[Y]
    (f: PolyFunc3)
    (implicit ff: f.P[X1, X2, X3, Y]): F[Y] =
      f(t._1, t._2, t._3)

  }

}
