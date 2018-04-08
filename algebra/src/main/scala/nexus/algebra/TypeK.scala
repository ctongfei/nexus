package nexus.algebra

/**
 * Runtime type information for a family of types parametrized by an axis specifier.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait TypeK[T[_]] {

  /** Given axes information `A`, returns type tag for grounded type `T[A]`. */
  def ground[A]: Type[T[A]]

}
