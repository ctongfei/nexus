package nexus.algebra

/**
 * @author Tongfei Chen
 */
trait IsUntypedTensor[T, @specialized(Float, Double, Boolean, Int, Long) E] {

  def unwrapScalar(x: T): E

  def wrapScalar(x: E): T

  def map(x: T)(f: E => E): T

  def map2(x1: T, x2: T)(f: (E, E) => E): T

  def map3(x1: T, x2: T, x3: T)(f: (E, E, E) => E): T

  //TODO: slice/broadcast/squeeze/expandDim/etc.

}
