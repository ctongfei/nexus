package nexus.storage

import nexus.{DType, Float32, Float64}

/**
 * @author Tongfei Chen
 */
class MemoryFloat32Storage (val data: Array[Float]) extends Storage[Float32] {

  def apply[@specialized R](i: Int)(implicit evo: DType.Ev[R, Float32]): R = evo.fromFloat(data(i))

  def update[@specialized R](i: Int, v: R)(implicit evo: DType.Ev[R, Float32]): Unit = data(i) = evo.toFloat(v)

}

class MemoryFloat64Storage private(val data: Array[Double]) extends Storage[Float64] {
  def apply[@specialized R](i: Int)(implicit ev: DType.Ev[R, Float64]): R = ev.fromDouble(data(i))
  def update[@specialized R](i: Int, v: R)(implicit ev: DType.Ev[R, Float64]): Unit = data(i) = ev.toDouble(v)
}
