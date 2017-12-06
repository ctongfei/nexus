package nexus

import nexus.algebra._

/**
 * A differentiable unary function.
 */
trait DOp1[X, Y] extends Op1[X, Y] {
  def tag: Grad[Y]

  /**
   * Performs gradient backpropagation.
   * @param dy Gradient of loss wrt ''y''
   * @param y Value of ''y''
   * @param x Value of ''x''
   * @return Gradient of ''x'': ∇x
   */
  def backward(dy: Y, y: Y, x: X): X
}

/**
 * A binary function that is differentiable with respect to at least one argument.
 */
trait DOp2[X1, X2, Y] extends Op2[X1, X2, Y] {
  def tag: Grad[Y]

  def backward1(dy: Y, y: Y, x1: X1, x2: X2): X1
  def backward2(dy: Y, y: Y, x1: X1, x2: X2): X2
}

/**
 * A ternary function that is differentiable with respect to at least one argument.
 */
trait DOp3[X1, X2, X3, Y] extends Op3[X1, X2, X3, Y] {
  def tag: Grad[Y]

  def backward1(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X1
  def backward2(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X2
  def backward3(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X3
}

