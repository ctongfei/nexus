package nexus

/**
 * @author Tongfei Chen
 */
sealed abstract class DType(val name: String) {
  override def toString = name
}

class Int32 extends DType("Int32")

class Int64 extends DType("Int64")

class Float32 extends DType("Float32")

class Float64 extends DType("Float64")
