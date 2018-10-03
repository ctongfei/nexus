package nexus

import nexus.exception._


/**
 * A unary function in computational graphs.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Op1[X, Y] extends Func1[X, Y] with AnyOp[Y] /*with PolyFunc1 */ {

  final def arity = 1

  /** Applies this operation to a symbolic expression. */
  def apply(x: Expr[X]): Expr[Y] = App1(this, x)

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

}

object Op1 {
  def fromFunction[A, B](f: A => B): Op1[A, B] = new Op1[A, B] {
    type Tag[b] = AnyType[b]
    def name = f.toString()
    def tag = AnyType[B]
    override def differentiable = false
    def forward(x: A) = f(x)
    def backward(dy: B, y: B, x: A) = throw new OperatorNotDifferentiableException(this, 1)
  }
}

/**
 * A binary function in computational graphs.
 * @see [[Op1]]
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Op2[X1, X2, Y] extends Func2[X1, X2, Y] with AnyOp[Y] {

  final def arity = 2

  /** Applies this operation to two symbolic expressions. */
  def apply(x1: Expr[X1], x2: Expr[X2]) = App2(this, x1, x2)

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

  def tupledOp: Op1[(X1, X2), Y] = new Op2.Tupled(this)
}

object Op2 {
  def fromFunction[A, B, C](f: (A, B) => C): Op2[A, B, C] = new Op2[A, B, C] {
    type Tag[c] = AnyType[c]
    def name = f.toString()
    def tag = AnyType[C]
    override def differentiable = false
    def forward(x1: A, x2: B) = f(x1, x2)
    def backward1(dy: C, y: C, x1: A, x2: B) = throw new OperatorNotDifferentiableException(this, 1)
    def backward2(dy: C, y: C, x1: A, x2: B) = throw new OperatorNotDifferentiableException(this, 2)
  }

  class Tupled[X1, X2, Y](val op: Op2[X1, X2, Y]) extends Op1[(X1, X2), Y] {
    type Tag[y] = op.Tag[y]
    def tag = op.tag
    def forward(x: (X1, X2)) = op.forward(x._1, x._2)
    def backward(dy: Y, y: Y, x: (X1, X2)) = (op.backward1(dy, y, x._1, x._2), op.backward2(dy, y, x._1, x._2))
    def name = s"${op.name}.tupled"
  }
}

/**
 * A ternary function in computational graphs.
 * @see [[Op1]], [[Op2]]
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Op3[X1, X2, X3, Y] extends Func3[X1, X2, X3, Y] with AnyOp[Y] {

  final def arity = 3

  /** Applies this operation to three symbolic expressions. */
  def forward(x1: X1, x2: X2, x3: X3): Y

  /** Applies this operation to three concrete values (forward computation). */
  def apply(x1: Expr[X1], x2: Expr[X2], x3: Expr[X3]) = App3(this, x1, x2, x3)

  def backward1(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X1
  def backward2(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X2
  def backward3(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X3

}
