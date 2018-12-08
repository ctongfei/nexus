package nexus.diff.instances

import nexus.diff._
import nexus.diff.ops._
import nexus.tensor._
import nexus.tensor.instances._

/**
 * @author Tongfei Chen
 */
object BoolExprIsBool extends IsBool[Symbolic[Boolean]] {
  def top = Const(true)
  def bot = Const(false)
  def not(a: Symbolic[Boolean]) = Not(a)
  def and(a: Symbolic[Boolean], b: Symbolic[Boolean]) = And(a, b)
  def or(a: Symbolic[Boolean], b: Symbolic[Boolean]) = Or(a, b)
  override def xor(a: Symbolic[Boolean], b: Symbolic[Boolean]) = Xor(a, b)
}
