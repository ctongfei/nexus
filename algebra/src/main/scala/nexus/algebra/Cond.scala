package nexus.algebra

/**
 * @author Tongfei Chen
 */
trait Cond[B, F[_]] {

  /** If-then-else construction. */
  def cond[A](c: B, t: F[A], f: F[A]): F[A]

}
