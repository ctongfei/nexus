package nexus

import nexus.algebra._

/**
 * A unary function in computational graphs.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Op1[X, Y] extends Module[X, Y] {

  /** Name of this operation. */
  def name: String

  def tag: Type[Y]

  /** Applies this operation to a symbolic expression. */
  def apply(x: Expr[X]): Expr[Y] = Apply1(this, x)

  /** Applies this operation to a concrete value (forward computation). */
  def forward(x: X): Y

  override def toString() = name
}

/**
 * A binary function in computational graphs.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Op2[X1, X2, Y] extends Module2[X1, X2, Y] {

  /** Name of this operation. */
  def name: String

  def tag: Type[Y]

  /** Applies this operation to two symbolic expressions. */
  def apply(x1: Expr[X1], x2: Expr[X2]) = Apply2(this, x1, x2)

  /** Applies this operation to two concrete values (forward computation). */
  def forward(x1: X1, x2: X2): Y

  override def toString() = name
}

/**
 * A ternary function in computational graphs.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Op3[X1, X2, X3, Y] extends Module3[X1, X2, X3, Y] {

  /** Name of this operation. */
  def name: String

  def tag: Type[Y]

  /** Applies this operation to three symbolic expressions. */
  def forward(x1: X1, x2: X2, x3: X3): Y

  /** Applies this operation to three concrete values (forward computation). */
  def apply(x1: Expr[X1], x2: Expr[X2], x3: Expr[X3]) = Apply3(this, x1, x2, x3)

  override def toString() = name
}

