package nexus.impl

/**
 * A typeclass attached on differentiable expressions ([[nexus.DExpr]])
 * or differentiable operators ([[nexus.DOp1]] etc.) that contains basic math operations on gradients.
 * These are used by optimizers ([[nexus.optimizer.Optimizer]]).
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait GradOps[H] {

  def zeroBy(x: H): H

  def add(x1: H, x2: H): H

  def addI(x1: H, x2: H): Unit

  def sub(x1: H, x2: H): H

  def neg(x: H): H

  def eMul(x1: H, x2: H): H

  def eDiv(x1: H, x2: H): H

  def scale(x: H, k: Double): H

}
