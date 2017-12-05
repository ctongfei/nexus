package nexus


trait PolyDOp1 extends PolyFOp1 {
  override type Op[X, Y] = DOp[X, Y]
}

trait PolyDOp2 extends PolyFOp2 {
  override type Op[X1, X2, Y] = DOp[X1, X2, Y]
}

trait PolyDOp3 extends PolyFOp3 {
  override type Op[X1, X2, X3, Y] = DOp[X1, X2, X3, Y]
}
