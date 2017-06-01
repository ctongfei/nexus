package nexus

/**
 * @author Tongfei Chen
 */
sealed trait DType

class Int32 extends DType
object Int32 extends Int32

class Int64 extends DType
object Int64 extends Int64

class Float32 extends DType
object Float32 extends Float32

class Float64 extends DType
object Float64 extends Float64
