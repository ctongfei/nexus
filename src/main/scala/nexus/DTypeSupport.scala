package nexus

/**
 * @author Tongfei Chen
 */
trait DTypeSupport[D <: DType, @specialized(Int, Long, Float, Double) R, S] {

  def newStorage(n: Int): S

  def get(storage: S, i: Int): R

  def set(storage: S, i: Int, x: R): Unit

}
