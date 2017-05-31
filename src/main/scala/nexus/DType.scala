package nexus

/**
 * @author Tongfei Chen
 */
sealed trait DType

object DType {

  trait Ev[@specialized(Int, Long, Float, Double) R, D <: DType] {
    def fromDouble(x: Double): R
    def toDouble(x: R): Double
    def fromFloat(x: Float): R
    def toFloat(x: R): Float
  }

  object Ev {
    implicit object int32ev extends Ev[Int, Int32] {
      def fromDouble(x: Double): Int = x.toInt
      def toDouble(x: Int): Double = x.toDouble
      def fromFloat(x: Float): Int = x.toInt
      def toFloat(x: Int): Float = x.toFloat
    }

    implicit object float32ev extends Ev[Float, Float32] {
      def fromDouble(x: Double): Float = x.toFloat
      def toDouble(x: Float): Double = x.toDouble
      def fromFloat(x: Float): Float = x
      def toFloat(x: Float): Float = x
    }

    implicit object float64ev extends Ev[Double, Float64] {
      def fromDouble(x: Double): Double = x
      def toDouble(x: Double): Double = x
      def fromFloat(x: Float): Double = x.toDouble
      def toFloat(x: Double): Float = x.toFloat
    }
  }

}

class Int32 extends DType
object Int32 extends Int32

class Float32 extends DType
object Float32 extends Float32

class Float64 extends DType
object Float64 extends DType
