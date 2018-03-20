package nexus.exception

import nexus._

/**
 * Exception thrown when attempting to differentiate an expression w.r.t. a non-differentiable variable.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class ExpressionNotDifferentiableException(e: Expr[_])
  extends Exception(s"Not differentiable with respect to expression $e.")

/**
 * Exception thrown when attempting to differentiate through an operator whose ''i''-th argument is not differentiable.
 * @param name
 * @param ordinal
 */
class OperatorNotDifferentiableException(name: String, ordinal: Int)
  extends Exception(s"Operator $name is not differentiable with respect to its ${ordinal match {
    case 1 => "1st"
    case 2 => "2nd"
    case 3 => "3rd"
    case n => s"${n}th" // actually will never happen: Nexus only allows operators with maximum arity 3
  }} argument.")

/**
 * Exception thrown when the value to one of the input in a computation instance is not specified.
 * @param e Input variable whose value is not specified
 */
class InputNotGivenException(e: Input[_])
  extends Exception(s"Value of input $e is not specified.")

class RankMismatchException extends Exception("Rank mismatch.")

/**
 * Exception thrown when attempting to convert a non-zero-dimensional tensor to a single element.
 */
class NotAZeroDimTensorException(x: Any)
  extends Exception(s"This tensor $x is not zero-dimensional.")

class NotAOneDimTensorException(x: Any)
  extends Exception(s"This tensor $x is not one-dimensional.")

class NotATwoDimTensorException(x: Any)
  extends Exception(s"This tensor $x is not two-dimensional.")
