package nexus.tensor

/**
 * Typeclass representing the ability to construct conditional (if-then-else) expressions.
 * @tparam B Type of conditions (a canonical version would be [[Boolean]])
 * @tparam F Higher-kinded type of supported `then`/`else` clauses
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Cond[B, F[_]] {

  /**
   * Constructs a conditional (if-then-else) expression.
   * @param c Condition
   * @param t Then-clause
   * @param f Else-clause
   * @tparam X Type of `then`/`else` clauses
   * @note {{{ cond(c, t, f) }}} is semantically equivalent to {{{ if (c) t else f }}}
   */
  def cond[X](c: B, t: F[X], f: F[X]): F[X]

}
