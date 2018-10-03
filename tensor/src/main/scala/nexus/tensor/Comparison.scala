package nexus.tensor

/**
 * @author Tongfei Chen
 */
trait Comparison[A, B] {

  def B: IsBool[B]

  def eq(a: A, b: A): B
  def ne(a: A, b: A): B
  def lt(a: A, b: A): B
  def gt(a: A, b: A): B = lt(b, a)
  def le(a: A, b: A): B
  def ge(a: A, b: A): B = le(b, a)

}
