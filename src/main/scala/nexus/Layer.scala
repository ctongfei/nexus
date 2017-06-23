package nexus

/**
 * @author Tongfei Chen
 */
trait Layer[X, Y] {

  type Input = X
  type Output = Y

  def apply(x: Expr[X]): Expr[Y]

  def parameters: Iterable[Param[_]]

}
