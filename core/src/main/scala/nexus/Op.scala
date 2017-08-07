package nexus

/**
 * Any unary neural (differentiable) function whose type parameters (axes, etc.) are fully grounded.
 * @author Tongfei Chen
 * @tparam X Type of input
 * @tparam Y Type of output
 * @since 0.1.0
 */
trait Op1[X, Y] extends Module[X, Y] {

  def name: String

  /** Apply this operation to a symbolic expression. */
  def apply(x: Expr[X]): Expr[Y] = Apply1(this, x)

  /** Apply this operation to a concrete value (forward computation). */
  def forward(x: X): Y

  def differentiableWrtX: Boolean = true

  /**
   * Performs gradient backpropagation.
   * @param dy Gradient of ''y'': ∇y
   * @param y Value of ''y''
   * @param x Value of ''x''
   * @return Gradient of ''x'': ∇x
   */
  def backward(dy : Y, y: Y, x: X): X

}


/**
 * Any binary neural (differentiable) function whose type parameters (axes, etc.) are fully grounded.
 * @author Tongfei Chen
 * @tparam X1 Type of first input
 * @tparam X2 Type of second input
 * @since 0.1.0
 */
trait Op2[X1, X2, Y] extends ((Expr[X1], Expr[X2]) => Expr[Y]) {
  def name: String
  def apply(x1: Expr[X1], x2: Expr[X2]) = Apply2(this, x1, x2)
  def forward(x1: X1, x2: X2): Y

  def differentiableWrtX1: Boolean = true
  def differentiableWrtX2: Boolean = true

  def backward1(dy: Y, y: Y, x1: X1, x2: X2): X1
  def backward2(dy: Y, y: Y, x1: X1, x2: X2): X2
}

/**
 * Any ternary neural (differentiable) function whose type parameters (axes, etc.) are fully grounded.
 * @author Tongfei Chen
 * @tparam X1 Type of first input
 * @tparam X2 Type of second input
 * @tparam X3 Type of third input
 */
trait Op3[X1, X2, X3, Y] extends ((Expr[X1], Expr[X2], Expr[X3]) => Expr[Y]) {
  def name: String
  def forward(x1: X1, x2: X2, x3: X3): Y
  def apply(x1: Expr[X1], x2: Expr[X2], x3: Expr[X3]) = Apply3(this, x1, x2, x3)

  def differentiableWrtX1: Boolean = true
  def differentiableWrtX2: Boolean = true
  def differentiableWrtX3: Boolean = true

  def backward1(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X1
  def backward2(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X2
  def backward3(dy: Y, y: Y, x1: X1, x2: X2, x3: X3): X3
}

