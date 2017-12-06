package nexus


trait PolyDOp1 extends PolyOp1 {
  type F[X, Y] <: DOp1[X, Y]
}

trait PolyDOp2 extends PolyOp2 {
  type F[X1, X2, Y] <: DOp2[X1, X2, Y]
}

trait PolyDOp3 extends PolyOp3 {
  type F[X1, X2, X3, Y] = DOp3[X1, X2, X3, Y]
}
