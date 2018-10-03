package nexus.execution

import cats._
import nexus._

/**
 * @author Tongfei Chen
 */
trait Backpropagation[F <: ForwardInterpreter] {

  def compute[R](e: Expr[R])(implicit R: IsReal[R], forward: F): ExprMap[Id]

}
