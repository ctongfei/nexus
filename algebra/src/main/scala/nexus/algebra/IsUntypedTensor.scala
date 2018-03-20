package nexus.algebra

/**
 * @author Tongfei Chen
 */
trait IsUntypedTensor[T, @specialized(Float, Double, Boolean, Int, Long) E] {

  def fromFlatArray(array: Array[E], shape: Array[Int]): T

  def unwrapScalar(x: T): E

  def wrapScalar(x: E): T

  def rank(x: T): Int

  def shape(x: T): Array[Int]

  def size(x: T) = shape(x).fold(1)(_*_)

  def map(x: T)(f: E => E): T

  def map2(x1: T, x2: T)(f: (E, E) => E): T

  def map3(x1: T, x2: T, x3: T)(f: (E, E, E) => E): T

  def slice(x: T, dim: Int, i: Int): T

  //TODO: slice/broadcast/squeeze/expandDim/etc.
  def expandDim(x: T, i: Int): T

}
