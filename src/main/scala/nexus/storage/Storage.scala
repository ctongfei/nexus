package nexus.storage

import nexus.DType

/**
 * @author Tongfei Chen
 */
trait Storage[D <: DType] {

  def apply[@specialized(Int, Long, Float, Double) R](i: Int)(implicit ev: DType.Ev[R, D]): R
  def update[@specialized(Int, Long, Float, Double) R](i: Int, v: R)(implicit ev: DType.Ev[R, D]): Unit

}
