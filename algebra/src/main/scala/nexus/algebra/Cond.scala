package nexus.algebra

/**
 * @author Tongfei Chen
 */
trait Cond[B, R[_]] {

  /** If-then-else construction. */
  def cond[A](c: B, t: R[A], f: R[A]): R[A]

}
