package nexus.algebra

/**
 * @author Tongfei Chen
 */
trait UntypedTensorOps[H, @specialized(Float, Double) R] {

  def unwrapScalar(x: H): R
  def wrapScalar(x: R): H

  def map(x: H)(f: R => R): H

  def map2(x1: H, x2: H)(f: (R, R) => R): H

  def map3(x1: H, x2: H, x3: H)(f: (R, R, R) => R): H

}
