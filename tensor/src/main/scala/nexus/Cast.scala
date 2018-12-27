package nexus

import cats._
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

object BidiCast {

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

  implicit def betweenInt[Z, Zʹ](implicit Z: IsInt[Z], Zʹ: IsInt[Zʹ]): BidiCast[Z, Zʹ] =
    new BidiCast[Z, Zʹ] {
      def invert(t: Zʹ) = Z.fromLong(Zʹ.toLong(t))
      def cast(s: Z) = Zʹ.fromLong(Z.toLong(s))
    }

  implicit def betweenReal[R, Rʹ](implicit R: IsReal[R], Rʹ: IsReal[Rʹ]): BidiCast[R, Rʹ] =
    new BidiCast[R, Rʹ] {
      def invert(t: Rʹ) = R.fromDouble(Rʹ.toDouble(t))
      def cast(s: R) = Rʹ.fromDouble(R.toDouble(s))
    }

}