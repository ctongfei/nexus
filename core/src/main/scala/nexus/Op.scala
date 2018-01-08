package nexus

import nexus.algebra._

/**
 * A unary function in computational graphs.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Op1[X, Y] extends Func1[X, Y] {

  /** Name of this operation. */
  def name: String

  /** Type tag of the output type. */
  def tag(tx: Type[X]): Type[Y]

  def differentiable: Boolean

  /** Applies this operation to a symbolic expression. */
  def apply(x: Expr[X]): Expr[Y] = Apply1(this, x)

  /** Applies this operation to a concrete value (forward computation). */
  def forward(x: X): Y

  /**
   * Performs gradient backpropagation.
   * @param dy Gradient of loss wrt ''y''
   * @param y Value of ''y''
   * @param x Value of ''x''
   * @return Gradient of loss wrt ''x''
   */
  def backward(dy: Y, y: Y, x: X): X

  override def toString() = name
}

/**
 * A binary function in computational graphs.
 * @see [[Op1]]
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Op2[X1, X2, Y] extends Func2[X1, X2, Y] {

  /** Name of this operation. */
  def name: String

  /** Type tag of the output type. */
  def tag(tx1: Type[X1], tx2: Type[X2]): Type[Y]

  def differentiable: Boolean

  /** Applies this operation to two symbolic expressions. */
  def apply(x1: Expr[X1], x2: Expr[X2]) = Apply2(this, x1, x2)

  /** Applies this operation to two concrete values (forward computation). */
  def forward(x1: X1, x2: X2): Y

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

  override def toString() = name
}

/**
 * A ternary function in computational graphs.
 * @see [[Op1]], [[Op2]]
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Op3[X1, X2, X3, Y] extends Func3[X1, X2, X3, Y] {

  /** Name of this operation. */
  def name: String

  /** Type tag of the output type. */
  def tag(tx1: Type[X1], tx2: Type[X2], tx3: Type[X3]): Type[Y]

  def differentiable: Boolean

  /** Applies this operation to three symbolic expressions. */
  def forward(x1: X1, x2: X2, x3: X3): Y

  /** Applies this operation to three concrete values (forward computation). */
  def apply(x1: Expr[X1], x2: Expr[X2], x3: Expr[X3]) = Apply3(this, x1, x2, x3)

  def backward1(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X1
  def backward2(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X2
  def backward3(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X3

  override def toString() = name
}
