package nexus

/**
 * Exception thrown when attempting to differentiate an expression w.r.t. a non-differentiable variable.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class ExpressionNotDifferentiableException(e: Expr[_])
  extends Exception(s"Not differentiable with respect to expression $e.")

class OperatorNotDifferentiableException(name: String)
  extends Exception(s"Operator $name is not differentiable.")
