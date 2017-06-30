package nexus

/**
 * @author Tongfei Chen
 */
trait Module[X, Y] extends (Expr[X] => Expr[Y]) {

  /** The parameters involved in this module. */
  def parameters: Seq[Param[_]]

}


trait Module2[X1, X2, Y] extends ((Expr[X1], Expr[X2]) => Expr[Y]) {

  def parameters: Seq[Param[_]]

}
