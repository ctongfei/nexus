package nexus

import cats._
import nexus.tensor._
import nexus.util._

/**
 * Represents a symbolic expression in a computational graph.
 * This is the core type in Nexus.
 * @tparam X Type of data that it conceptually holds
 * @since 0.1.0
 * @author Tongfei Chen
 */
sealed trait Symbolic[X] {

  /** Type tag of this expression. */
  def tag: Tag[X]

  /** Will the gradient of this expression be computed when performing backward computation? */
  def requireGrad: Boolean

  /**
   * Gets the value of this expression given an implicit computation instance,
   * while forcing this expression to be evaluated strictly in that specific
   * computation instance.
   */
  def value(implicit comp: Symbolic ~> Id): X = comp(this)

  def !>[Y](f: X => Y): Symbolic[Y] = Op1.fromFunction(f)(this)

  /** Passes this expression through a function. */
  def |>[Y](f: Func1[X, Y]): Symbolic[Y] = f(this)

  /** Passes this expression through a type-polymorphic function. */
  def |>[Y](f: PolyFunc1)(implicit ff: f.F[X, Y]): Symbolic[Y] = f(this)

  /** Creates an assignment to this expression. */
  def :=(value: X): Assignment = Assignment(this, value)

  /** Substitutes an input to an expression in this expression. */
  def substitute[E](a: Input[E], b: Symbolic[E]): Symbolic[X] = this match {
    case x: Input[X] => if (x eq a) b.asInstanceOf[Symbolic[X]] else x // this cast is safe: (x eq a) proves that E =:= X
    case x: Const[X] => x
    case x: Param[X] => x
    case App1(op, x) => App1(op, x.substitute(a, b))
    case App2(op, x1, x2) => App2(op, x1.substitute(a, b), x2.substitute(a, b))
    case App3(op, x1, x2, x3) => App3(op, x1.substitute(a, b), x2.substitute(a, b), x3.substitute(a, b))
  }
}

/**
 * A placeholder for inputs to a computation graph.
 */
case class Input[X](name: String = ExprName.nextInput) extends Symbolic[X] { self =>

  def tag = Tag.any[X] // no need to compute the gradient of the input
  def requireGrad = false

  /** Constructs a neural function (lambda expression). */
  def =>>[Y](y: Symbolic[Y]): Lambda1[X, Y] = Lambda1(this, y)

  override def toString = name

}


/**
 * A parameter of a model.
 * @param value Initial value of this parameter
 * @note A `Param` has to be differentiable by providing a `Grad[X]` instance as its type tag.
 */
case class Param[X](var value: X, name: String)(implicit grad: Grad[X]) extends Symbolic[X] {

  final def requireGrad = true // or else, how could it be updated?

  def tag: Tag.Aux[X, Grad] = Tag.of(grad)

  private[this] val ev = tag.ev

  def +=(g: X): Unit = if (ev.mutable)
    ev.addI(value, g)
  else value = ev.add(value, g)

  def -=(g: X): Unit = +=(ev.neg(g))

  override def toString = name

}


/**
 * A constant value in a computational graph.
 * @param value Value of this constant
 */
case class Const[X](value: X, name: String = ExprName.nextConst) extends Symbolic[X] {

  final def tag = Tag.any[X]
  override def requireGrad = false
  override def toString = name

}


/**
 * The result of the application of a unary function to an expression.
 */
case class App1[X, Y](op: Op1[X, Y], x: Symbolic[X]) extends Symbolic[Y] {
  type Input = X
  type Output = Y
  val requireGrad = op.differentiable && x.requireGrad
  def tag = op.tag
  override def toString = s"${op.name}($x)"
}


/**
 * The result of the application of a binary function to two expressions.
 */
case class App2[X1, X2, Y](op: Op2[X1, X2, Y], x1: Symbolic[X1], x2: Symbolic[X2]) extends Symbolic[Y] {
  type Input1 = X1
  type Input2 = X2
  type Output = Y

  val requireGrad = op.differentiable && (x1.requireGrad || x2.requireGrad)
  def tag = op.tag
  override def toString = s"${op.name}($x1, $x2)"
}


/**
 * The result of the application of a ternary function to three expressions.
 */
case class App3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: Symbolic[X1], x2: Symbolic[X2], x3: Symbolic[X3]) extends Symbolic[Y] {
  type Input1 = X1
  type Input2 = X2
  type Input3 = X3
  type Output = Y

  val requireGrad = op.differentiable && (x1.requireGrad || x2.requireGrad || x3.requireGrad)
  def tag = op.tag
  override def toString = s"${op.name}($x1, $x2, $x3)"
}
