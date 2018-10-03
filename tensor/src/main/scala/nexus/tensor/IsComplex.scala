package nexus.tensor

import algebra.ring._

/**
 * Typeclass representing operations on complex numbers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait IsComplex[C, R] extends Field[C] {

  def R: IsReal[R]
  def re(z: C): R
  def im(z: C): R
  def complex(x: R, y: R): C
  def arg(z: C): R

}
