package nexus

/**
 * @author Tongfei Chen
 */
abstract class ParamPolyOp1 { self =>

  trait F[-P, X, Y] <: (P => Op1[X, Y])

  class Proxy[P](val parameter: P) extends ParamPolyOp1Proxy[P](parameter) {
    type F[-P, X, Y] = self.F[P, X, Y]
  }

  def apply[P](parameter: P) = new Proxy(parameter)

}

abstract class ParamPolyOp1Proxy[P](parameter: P) {

  type F[-P, X, Y] <: (P => Op1[X, Y])

  def apply[X, Y](x: Expr[X])(implicit f: F[P, X, Y]): Expr[Y] = Apply1(f(parameter), x)

}


abstract class ParamPolyOp2 { self =>

  trait F[-P, X1, X2, Y] <: (P => Op2[X1, X2, Y])

  class Proxy[P](val parameter: P) extends ParamPolyOp2Proxy[P](parameter) {
    type F[-P, X1, X2, Y] = self.F[P, X1, X2, Y]
  }

  def apply[P](parameter: P) = new Proxy(parameter)

}

abstract class ParamPolyOp2Proxy[P](parameter: P) {

  type F[-P, X1, X2, Y] <: (P => Op2[X1, X2, Y])

  def apply[X1, X2, Y](x1: Expr[X1], x2: Expr[X2])(implicit f: F[P, X1, X2, Y]): Expr[Y] = Apply2(f(parameter), x1, x2)

}

abstract class ParamPolyOp3 { self =>

  trait F[-P, X1, X2, X3, Y] <: (P => Op3[X1, X2, X3, Y])

  class Proxy[P](val parameter: P) extends ParamPolyOp3Proxy[P](parameter) {
    type F[-P, X1, X2, X3, Y] <: (P => Op3[X1, X2, X3, Y])
  }

  def apply[P](parameter: P) = new Proxy(parameter)

}

abstract class ParamPolyOp3Proxy[P](parameter: P) {

  type F[-P, X1, X2, X3, Y] <: (P => Op3[X1, X2, X3, Y])

  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: Expr[X2], x3: Expr[X3])(implicit f: F[P, X1, X2, X3, Y]): Expr[Y] =
    Apply3(f(parameter), x1, x2, x3)

}
