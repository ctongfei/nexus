package nexus.algebra

import nexus.algebra.util._

import scala.reflect._

/**
 * @author Tongfei Chen
 */
trait IsUntypedTensor[T, @specialized(Float, Double, Boolean, Int, Long) E] {

  implicit val elementTypeClassTag: ClassTag[E]

  def tabulate(shape: Array[Int])(f: Array[Int] => E): T = {
    val flatArray = Indices.indices(shape).map(f).toArray(elementTypeClassTag)
    fromFlatArray(flatArray, shape)
  }

  def fromFlatArray(array: Array[E], shape: Array[Int]): T

  def get(x: T, is: Array[Int]): E

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
