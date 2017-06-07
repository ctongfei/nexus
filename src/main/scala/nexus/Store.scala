package nexus

import scala.annotation._

/**
 * Proves that arrays of [[D]] elements (showing in JVM as [[R]]) can be stored in an [[H]] object.
 * @author Tongfei Chen
 */
@implicitNotFound("This device does not support storing an array of ${D} elements.")
trait Store[D <: DType, @specialized(Int, Long, Float, Double) R, H] {

  def allocate(n: Int): H

  def fromArray(array: Array[R]): H

  def toArray(handle: H): Array[R]

  def get(handle: H, i: Int): R

  def set(handle: H, i: Int, x: R): Unit

}
