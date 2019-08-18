package nexus

import cats._
import nexus.typelevel._

trait TensorMonad[F[_, _]] { self =>

  def pure[A](a: => A): F[A, Unit]
  def map[A, B, U](fa: F[A, U])(f: A => B): F[B, U]
  def flatMap[A, B, U, V, W](fa: F[A, U])(f: A => F[B, V])(implicit w: Union.Aux[U, V, W]): F[B, W]

}
