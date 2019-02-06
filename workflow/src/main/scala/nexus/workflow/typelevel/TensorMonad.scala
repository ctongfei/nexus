package nexus.workflow.typelevel

import nexus.typelevel._

/**
 * @author Tongfei Chen
 */
trait TensorMonad[T[_, _]] {

  def pure[A](x: A): T[A, Unit]

  def map[U, A, B](ta: T[A, U])(f: A => B): T[B, U]

  def flatMap[U, V, W, A, B](ta: T[A, U])(f: A => T[V, B])(implicit w: Union.Aux[U, V, W]): T[B, W]

  def product[U, V, W, A, B](ta: T[A, U], tb: T[B, V])(implicit w: Union.Aux[U, V, W]): T[(A, B), W]

}
