package nexus.algebra

/**
 * @author Tongfei Chen
 */
trait IsUntypedTensor[H, @specialized(Float, Double, Boolean, Int, Long) X] {

  def unwrapScalar(x: H): X
  def wrapScalar(x: X): H

  def map(x: H)(f: X => X): H

  def map2(x1: H, x2: H)(f: (X, X) => X): H

  def map3(x1: H, x2: H, x3: H)(f: (X, X, X) => X): H

  //TODO: slice/broadcast/squeeze/expandDim/etc.

}
