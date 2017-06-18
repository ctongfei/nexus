import shapeless.HNil

/**
 * @author Tongfei Chen
 */
package object nexus {

  type ~~>[X, Y] = Op1[X, Y]

  sealed abstract class DType(val name: String) {
    override def toString = name
  }

  sealed class Int32 extends DType("Int32")
  sealed class Int64 extends DType("Int64")
  sealed class Float32 extends DType("Float32")
  sealed class Float64 extends DType("Float64")

  val Int32 = new Int32
  val Int64 = new Int64
  val Float32 = new Float32
  val Float64 = new Float64


}
