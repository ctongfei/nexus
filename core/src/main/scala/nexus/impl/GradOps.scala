package nexus.impl

/**
 * @author Tongfei Chen
 */
trait GradOps[H] {

  def add(x1: H, x2: H): H
  def addI(x1: H, x2: H): Unit

  def sub(x1: H, x2: H): H

  def neg(x: H): H

  def eMul(x1: H, x2: H): H

  def eDiv(x1: H, x2: H): H

  def scale(x: H, k: Double): H

}
