package nexus

import cats._
import cats.arrow.Category
import cats.evidence._

/**
 * Proves that values of type [[S]] can be cast to a value of type [[T]].
 * @author Tongfei Chen
 */
trait Cast[S, T] {
  import Cast._

  def cast(s: S): T

  def compose[R](that: Cast[R, S]): Cast[R, T] = new Composed(that, this)
  def andThen[U](that: Cast[T, U]): Cast[S, U] = new Composed(this, that)
}

object Cast {

  class Composed[R, S, T](RS: Cast[R, S], ST: Cast[S, T]) extends Cast[R, T] {
    def cast(s: R) = ST.cast(RS.cast(s))
  }

  implicit def fromCatsAs[S, T](implicit ev: S As T): Cast[S, T] = new Cast[S, T] {
    def cast(s: S) = ev.coerce(s)
  }

  implicit object Category extends Category[Cast] {
    def id[A] = fromCatsAs(As.refl[A])
    def compose[A, B, C](f: Cast[B, C], g: Cast[A, B]) = new Composed(g, f)
  }
}

/**
 * Proves that values of type [[S]] can be cast to a value of type [[T]], and vice versa.
 * @author Tongfei Chen
 */
trait BidiCast[S, T] extends Cast[S, T] { self =>
  import BidiCast._

  def invert(t: T): S

  def inverse: BidiCast[T, S] = new Inverse(this)
  def compose[R](that: BidiCast[R, S]): BidiCast[R, T] = new Composed(that, this)
  def andThen[U](that: BidiCast[T, U]): BidiCast[S, U] = new Composed(this, that)
}

object BidiCast extends BidiCastBetweenAnyVals {

  class Composed[R, S, T](RS: BidiCast[R, S], ST: BidiCast[S, T]) extends Cast.Composed[R, S, T](RS, ST) with BidiCast[R, T] {
    def invert(t: T) = RS.invert(ST.invert(t))
  }

  class Inverse[S, T](ST: BidiCast[S, T]) extends BidiCast[T, S] {
    def invert(t: S) = ST.cast(t)
    def cast(s: T) = ST.invert(s)
    override def inverse = ST
  }

  implicit def fromCatsIs[S, T](implicit ev: S Is T): BidiCast[S, T] = new BidiCast[S, T] {
    def cast(s: S) = ev.coerce(s)
    def invert(t: T) = ev.flip.coerce(t)
  }

  implicit def inverse[S, T](implicit ST: BidiCast[S, T]): BidiCast[T, S] = ST.inverse

  implicit object Category extends Category[BidiCast] {
    override def id[A] = fromCatsIs(Is.refl[A])
    override def compose[A, B, C](f: BidiCast[B, C], g: BidiCast[A, B]) = new Composed(g, f)
  }

}


trait BidiCastBetweenAnyVals {
  // Bidirectional casting between Boolean, Byte, Short, Int, Long, Float, Double
  // These are the numeric types that Nexus supports

  implicit object BooleanByteBidiCast extends BidiCast[Boolean, Byte] {
    def cast(s: Boolean) = if (s) 1 else 0
    def invert(t: Byte) = t != 0
  }
  implicit object BooleanShortBidiCast extends BidiCast[Boolean, Short] {
    def cast(s: Boolean) = if (s) 1 else 0
    def invert(t: Short) = t != 0
  }
  implicit object BooleanIntBidiCast extends BidiCast[Boolean, Int] {
    def cast(s: Boolean) = if (s) 1 else 0
    def invert(t: Int) = t != 0
  }
  implicit object BooleanLongBidiCast extends BidiCast[Boolean, Long] {
    def cast(s: Boolean) = if (s) 1 else 0
    def invert(t: Long) = t != 0
  }
  implicit object BooleanFloatBidiCast extends BidiCast[Boolean, Float] {
    def cast(s: Boolean) = if (s) 1f else 0f
    def invert(t: Float) = t != 0f
  }
  implicit object BooleanDoubleBidiCast extends BidiCast[Boolean, Double] {
    def cast(s: Boolean) = if (s) 1d else 0d
    def invert(t: Double) = t != 0d
  }
  implicit object ByteShortBidiCast extends BidiCast[Byte, Short] {
    def cast(s: Byte) = s.toShort
    def invert(t: Short) = t.toByte
  }
  implicit object ByteIntBidiCast extends BidiCast[Byte, Int] {
    def cast(s: Byte) = s.toInt
    def invert(t: Int) = t.toByte
  }
  implicit object ByteLongBidiCast extends BidiCast[Byte, Long] {
    def cast(s: Byte) = s.toLong
    def invert(t: Long) = t.toByte
  }
  implicit object ByteFloatBidiCast extends BidiCast[Byte, Float] {
    def cast(s: Byte) = s.toFloat
    def invert(t: Float) = t.toByte
  }
  implicit object ByteDoubleBidiCast extends BidiCast[Byte, Double] {
    def cast(s: Byte) = s.toDouble
    def invert(t: Double) = t.toByte
  }
  implicit object ShortIntBidiCast extends BidiCast[Short, Int] {
    def cast(s: Short) = s.toInt
    def invert(t: Int) = t.toShort
  }
  implicit object ShortLongBidiCast extends BidiCast[Short, Long] {
    def cast(s: Short) = s.toLong
    def invert(t: Long) = t.toShort
  }
  implicit object ShortFloatBidiCast extends BidiCast[Short, Float] {
    def cast(s: Short) = s.toFloat
    def invert(t: Float) = t.toShort
  }
  implicit object ShortDoubleBidiCast extends BidiCast[Short, Double] {
    def cast(s: Short) = s.toDouble
    def invert(t: Double) = t.toShort
  }
  implicit object IntLongBidiCast extends BidiCast[Int, Long] {
    def cast(s: Int) = s.toLong
    def invert(t: Long) = t.toInt
  }
  implicit object IntFloatBidiCast extends BidiCast[Int, Float] {
    def cast(s: Int) = s.toFloat
    def invert(t: Float) = t.toInt
  }
  implicit object IntDoubleBidiCast extends BidiCast[Int, Double] {
    def cast(s: Int) = s.toDouble
    def invert(t: Double) = t.toInt
  }
  implicit object LongFloatBidiCast extends BidiCast[Long, Float] {
    def cast(s: Long) = s.toFloat
    def invert(t: Float) = t.toLong
  }
  implicit object LongDoubleBidiCast extends BidiCast[Long, Double] {
    def cast(s: Long) = s.toDouble
    def invert(t: Double) = t.toLong
  }
  implicit object FloatDoubleBidiCast extends BidiCast[Float, Double] {
    def cast(s: Float) = s.toDouble
    def invert(t: Double) = t.toFloat
  }
}
