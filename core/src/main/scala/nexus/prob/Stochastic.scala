package nexus.prob

import cats._
import nexus.algebra._

import scala.collection._
import scala.util._

/**
 * Represents a stochastic variable that enables monadic composition of probabilistic computations.
 * @tparam A Support of this stochastic variable
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Stochastic[+A] { self =>

  /** Draws a sample. */
  def sample: A

  /** Lazily draws infinitely many samples. */
  def samples: Iterable[A] = new AbstractIterable[A] {
    def iterator = new AbstractIterator[A] {
      def hasNext = true
      def next() = sample
    }
  }

  def map[B](f: A => B): Stochastic[B] = Stochastic.from(f(sample))

  def flatMap[B](f: A => Stochastic[B]): Stochastic[B] = Stochastic.from(f(sample).sample)

  def filter(f: A => Boolean): Stochastic[A] = new Stochastic.Conditional(self, f)

  def collect[B](pf: PartialFunction[A, B]): Stochastic[B] = new Stochastic.ConditionallyMapped(self, pf)

  def product[B](that: Stochastic[B]): Stochastic[(A, B)] = Stochastic.from(self.sample, that.sample)

  def productWith[B, C](that: Stochastic[B])(f: (A, B) => C): Stochastic[C] = Stochastic.from(f(self.sample, that.sample))

  def repeatToSeq(n: Int): Stochastic[Seq[A]] = Stochastic.from(Seq.fill(n)(sample))

  def repeatToTensor[T, Aʹ >: A](shape: Seq[Int])(implicit T: IsTensor[T, Aʹ]): Stochastic[T] = ???
    //(Array.fill(shape.product)(sample), shape.toArray)

}

object Stochastic {

  def from[A](f: => A): Stochastic[A] = new AbstractStochastic[A] {
    def sample = f
  }

  implicit object Monad extends Monad[Stochastic] {
    def pure[A](x: A) = from(x)
    def flatMap[A, B](fa: Stochastic[A])(f: A => Stochastic[B]) = fa.flatMap(f)
    override def map[A, B](fa: Stochastic[A])(f: A => B) = fa.map(f)
    def tailRecM[A, B](a: A)(f: A => Stochastic[Either[A, B]]) = f(a) collect { case Right(b) => b }
  }

  class Conditional[A, B](self: Stochastic[A], f: A => Boolean) extends AbstractStochastic[A] {
    def sample: A = {
      do {
        val x = self.sample
        if (f(x)) return x
      } while (true)
      null.asInstanceOf[A] // will never happen
    }
  }

  class ConditionallyMapped[A, B](self: Stochastic[A], pf: PartialFunction[A, B]) extends AbstractStochastic[B] {
    val f = pf.lift
    def sample: B = {
      do {
        val x = self.sample
        f(x) match {
          case Some(y) => return y
          case None =>
        }
      } while (true)
      null.asInstanceOf[B] // will never happen
    }
  }

}

// Reduce bytecode size
abstract class AbstractStochastic[+A] extends Stochastic[A]
