package nexus.exception

import nexus._

/**
 * Exception thrown when attempting to differentiate an expression w.r.t. a non-differentiable variable.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class ExpressionNotDifferentiableException(e: Expr[_])
  extends Exception(s"Not differentiable with respect to expression $e.")

class OperatorNotDifferentiableException(name: String, ordinal: Int)
  extends Exception(s"Operator $name is not differentiable with respect to its ${ordinal match {
    case 1 => "1st"
    case 2 => "2nd"
    case 3 => "3rd"
    case n => s"${n}th" // actually will never happen: Nexus only allows operators with maximum arity 3
  }} argument.")

class InputNotGivenException(e: Input[_])
  extends Exception(s"Value of input $e is not specified.")
