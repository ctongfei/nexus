package nexus.diff.instances

import nexus.diff._
import nexus.diff.ops._
import nexus._
import nexus.diff.util._
import nexus.instances._

/**
 * @author Tongfei Chen
 */
object SymbolicBoolIsBool extends IsBool[Symbolic[Boolean]] {

  def fromBoolean(b: Boolean) = Symbolic.Algebra.const(b, ExprName.nextConst)

  def top = Symbolic.Algebra.const(true, "true")
  def bot = Symbolic.Algebra.const(false, "false")
  def not(a: Symbolic[Boolean]) = Not(a)
  def and(a: Symbolic[Boolean], b: Symbolic[Boolean]) = And(a, b)
  def or(a: Symbolic[Boolean], b: Symbolic[Boolean]) = Or(a, b)
  override def xor(a: Symbolic[Boolean], b: Symbolic[Boolean]) = Xor(a, b)
}
