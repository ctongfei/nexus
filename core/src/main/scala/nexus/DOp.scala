package nexus

import nexus.algebra._

/**
 * A differentiable unary function.
 * @see [[Op1]]
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait DOp1[X, Y] extends Op1[X, Y] {

  /** Type tag of the output. */
  def tag: Grad[Y]

  /**
   * Performs gradient backpropagation.
   * @param dy Gradient of loss wrt ''y''
   * @param y Value of ''y''
   * @param x Value of ''x''
   * @return Gradient of loss wrt ''x''
   */
  def backward(dy: Y, y: Y, x: X): X
}

/**
 * A binary function that is differentiable with respect to at least one argument.
 * @see [[DOp1]], [[Op2]]
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait DOp2[X1, X2, Y] extends Op2[X1, X2, Y] {

  /** Type tag of the output. */
  def tag: Grad[Y]

  /**
   * Performs gradient backpropagation wrt the first input.
   * @param dy Gradient of loss wrt ''y''
   * @param y Value of ''y''
   * @param x1 Value of ''x'',,1,,
   * @param x2 Value of ''x'',,2,,
   * @return Gradient of loss wrt ''x'',,1,,
   */
  def backward1(dy: Y, y: Y, x1: X1, x2: X2): X1

  /**
   * Performs gradient backpropagation wrt the second input.
   * @param dy Gradient of loss wrt ''y''
   * @param y Value of ''y''
   * @param x1 Value of ''x'',,1,,
   * @param x2 Value of ''x'',,2,,
   * @return Gradient of loss wrt ''x'',,2,,
   */
  def backward2(dy: Y, y: Y, x1: X1, x2: X2): X2
}

/**
 * A ternary function that is differentiable with respect to at least one argument.
 * @see [[DOp1]], [[Op3]]
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait DOp3[X1, X2, X3, Y] extends Op3[X1, X2, X3, Y] {

  /** Type tag of the output. */
  def tag: Grad[Y]

  def backward1(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X1
  def backward2(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X2
  def backward3(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X3
}

