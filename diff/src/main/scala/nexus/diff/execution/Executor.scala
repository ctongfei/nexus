package nexus.diff.execution

import cats._
import nexus.diff._

/**
 * Base trait for a finally tagless interpreter.
 * @author Tongfei Chen
 */
trait Interpreter[F[_]] extends (Symbolic ~> F) {

  def const[X](x: Const[X]): F[X]
  def input[X](x: Input[X]): F[X]
  def param[X](x: Param[X]): F[X]

  def app1[X, Y](f: Op1[X, Y], x: F[X]): F[Y]
  def app2[X1, X2, Y](f: Op2[X1, X2, Y], x1: F[X1], x2: F[X2]): F[Y]
  def app3[X1, X2, X3, Y](f: Op3[X1, X2, X3, Y], x1: F[X1], x2: F[X2], x3: F[X3]): F[Y]

  def apply[A](y: Symbolic[A]): F[A] = y match {
    case x: Const[A] => const(x)
    case x: Input[A] => input(x)
    case x: Param[A] => param(x)
    case App1(f, x) => app1(f, apply(x))
    case App2(f, x1, x2) => app2(f, apply(x1), apply(x2))
    case App3(f, x1, x2, x3) => app3(f, apply(x1), apply(x2), apply(x3))
  }
}
