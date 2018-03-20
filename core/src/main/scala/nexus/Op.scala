package nexus

import nexus.algebra._
import nexus.exception._

/**
 * A unary function in computational graphs.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Op1[X, Y] extends Func1[X, Y] with OpBase /*with PolyFunc1 */ {

  type F[Xʹ, Yʹ] = (X, Y) =:= (Xʹ, Yʹ)
  def ground[X, Y](implicit f: F[X, Y]) = this.asInstanceOf[Op1[X, Y]]

  /** Type tag of the output type. */
  def tag(tx: Type[X]): Type[Y]

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
    def name = f.toString()
    def tag(tx: Type[A]) = Type.nonDifferentiable[B]
    override def differentiable = false
    def forward(x: A) = f(x)
    def backward(dy: B, y: B, x: A) = throw new OperatorNotDifferentiableException(name, 1)
  }
}

/**
 * A binary function in computational graphs.
 * @see [[Op1]]
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Op2[X1, X2, Y] extends Func2[X1, X2, Y] with OpBase {

  type F[X1ʹ, X2ʹ, Yʹ] = ((X1, X2, Y) =:= (X1ʹ, X2ʹ, Yʹ))
  def ground[X1, X2, Y](implicit f: F[X1, X2, Y]) = this.asInstanceOf[Op2[X1, X2, Y]]

  /** Type tag of the output type. */
  def tag(tx1: Type[X1], tx2: Type[X2]): Type[Y]

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

}

object Op2 {
  def fromFunction[A, B, C](f: (A, B) => C): Op2[A, B, C] = new Op2[A, B, C] {
    def name = f.toString()
    def tag(tx1: Type[A], tx2: Type[B]) = Type.nonDifferentiable[C]
    override def differentiable = false
    def forward(x1: A, x2: B) = f(x1, x2)
    def backward1(dy: C, y: C, x1: A, x2: B) = throw new OperatorNotDifferentiableException(name, 1)
    def backward2(dy: C, y: C, x1: A, x2: B) = throw new OperatorNotDifferentiableException(name, 2)
  }
}

/**
 * A ternary function in computational graphs.
 * @see [[Op1]], [[Op2]]
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Op3[X1, X2, X3, Y] extends Func3[X1, X2, X3, Y] with OpBase {

  type F[X1ʹ, X2ʹ, X3ʹ, Yʹ] = ((X1, X2, X3, Y) =:= (X1ʹ, X2ʹ, X3ʹ, Yʹ))
  def ground[X1, X2, X3, Y](implicit f: F[X1, X2, X3, Y]) = this.asInstanceOf[Op3[X1, X2, X3, Y]]

  /** Type tag of the output type. */
  def tag(tx1: Type[X1], tx2: Type[X2], tx3: Type[X3]): Type[Y]

  /** Applies this operation to three symbolic expressions. */
  def forward(x1: X1, x2: X2, x3: X3): Y

  /** Applies this operation to three concrete values (forward computation). */
  def apply(x1: Expr[X1], x2: Expr[X2], x3: Expr[X3]) = App3(this, x1, x2, x3)

  def backward1(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X1
  def backward2(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X2
  def backward3(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X3

}

/**
 * Some basic definitions on operators.
 */
trait OpBase {
  /** Name of this operation. */
  def name: String
  def differentiable: Boolean = true
  override def toString() = name
}
