package nexus.instances

import nexus._
import nexus.algebra._
import nexus.ops._

/**
 * @author Tongfei Chen
 */
object BoolExprIsBool extends IsGenBool[Expr, Boolean] {
  def top = Const(true)
  def bot = Const(false)
  def not(a: Expr[Boolean]) = Not(a)
  def and(a: Expr[Boolean], b: Expr[Boolean]) = And(a, b)
  def or(a: Expr[Boolean], b: Expr[Boolean]) = Or(a, b)
  override def xor(a: Expr[Boolean], b: Expr[Boolean]) = Xor(a, b)

  def cond[A](c: Expr[Bool], t: Expr[A], f: Expr[A]) = If(c, t, f)(If.ifF(t.tag))
}
