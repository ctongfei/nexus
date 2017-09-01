package nexus


trait PolyDOp1[F[X, Y] <: DOp1[X, Y]] extends PolyFOp1[F, F]
trait PolyDOp2[F[X1, X2, Y] <: DOp2[X1, X2, Y]] extends PolyFOp2[F, F]
trait PolyDOp3[F[X1, X2, X3, Y] <: DOp3[X1, X2, X3, Y]] extends PolyFOp3[F, F]
