package nexus

/**
 * @author Tongfei Chen
 */
trait ParaPolyDOp1[P] extends ParaPolyOp1[P] { self =>
  type F[P, X, Y] <: (P => DOp1[X, Y])

}

trait ParaPolyDOp2[P] extends ParaPolyOp2[P] { self =>

  type F[P, X1, X2, Y] <: (P => DOp2[X1, X2, Y])
}

trait ParaPolyDOp3[P] extends ParaPolyOp3[P] { self =>

  type F[P, X1, X2, X3, Y] <: (P => DOp3[X1, X2, X3, Y])

}
