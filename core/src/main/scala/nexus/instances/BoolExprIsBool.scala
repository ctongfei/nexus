package nexus.instances

import nexus._
import nexus.ops._
import nexus.tensor._
import nexus.tensor.instances.all._

/**
 * @author Tongfei Chen
 */
object BoolExprIsBool extends IsBool[Expr[Boolean]] {
  def top = Const(true)
  def bot = Const(false)
  def not(a: Expr[Boolean]) = Not(a)
  def and(a: Expr[Boolean], b: Expr[Boolean]) = And(a, b)
  def or(a: Expr[Boolean], b: Expr[Boolean]) = Or(a, b)
  override def xor(a: Expr[Boolean], b: Expr[Boolean]) = Xor(a, b)

}
