package nexus.diff

import cats._

/**
 * Typeclass witnessing a structure `S[_]` can be unrolled under the computation box `F[_]`.
 * For example, a traced sequence `Traced[Seq[A]]` can be unrolled to `Seq[Traced[A]]`.
 *
 * This is a more specific abstraction than [[cats.Traverse]].
 * @author Tongfei Chen
 */
trait Unroll[F[_], S[_]] {

  def traverse[A, B](fa: F[A])(f: A *=> S[B]): S[F[B]]

  def unroll[A](fsa: F[S[A]]): S[F[A]]

}

object Unroll {

  implicit def unrollAnyId[S[_]]: Unroll[Id, S] = new Unroll[Id, S] {
    def traverse[A, B](a: A)(f: A *=> S[B]) = f(a)
    def unroll[A](sa: S[A]) = sa
  }

}
