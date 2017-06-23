package nexus

/**
 * @author Tongfei Chen
 */
trait GenLayer[F[X, Y] <: Layer[X, Y]] {

  def apply[X, Y](x: Expr[X])(implicit f: F[X, Y]): Expr[Y] = f(x)

}
