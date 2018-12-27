package nexus.prob

import cats._
import nexus._
import scala.collection._
import scala.reflect._
import scala.util._

/**
 * Represents a stochastic variable that enables monadic composition of probabilistic computations.
 * @tparam A Support of this stochastic variable
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Stochastic[+A] { self =>

  import Stochastic._

  /** Draws a sample from this stochastic variable. */
  def sample: A

  /** Lazily draws infinitely many samples. */
  def samples: Iterable[A] =
    new InfiniteSamples(self)

  def map[B](f: A => B): Stochastic[B] = Stochastic {
    f(sample)
  }

  def flatMap[B](f: A => Stochastic[B]): Stochastic[B] = Stochastic {
    f(sample).sample
  }

  /** Creates a conditional stochastic variable conditioned on the given predicate. */
  def filter(f: A => Boolean): Stochastic[A] =
    new Conditional(self, f)

  /** Creates a conditional stochastic variable conditioned on the given predicate. Alias of [[filter]]. */
  def given(f: A => Boolean): Stochastic[A] = filter(f)

  def collect[B](pf: PartialFunction[A, B]): Stochastic[B] =
    new ConditionallyMapped(self, pf)

  /**
   * Creates a joint distribution of two stochastic variables, assuming that they are independent.
   * This is the [[cats.Applicative]] `product` function on [[nexus.prob.Stochastic]].
   */
  def product[B](that: Stochastic[B]): Stochastic[(A, B)] = Stochastic {
    (self.sample, that.sample)
  }

  def productWith[B, C](that: Stochastic[B])(f: (A, B) => C): Stochastic[C] = Stochastic {
    f(self.sample, that.sample)
  }

  def repeatToArray[A1 >: A](n: Int)(implicit ct: ClassTag[A1]): Stochastic[Array[A1]] = Stochastic {
    Array.fill(n)(sample)
  }

  def repeatToSeq(n: Int): Stochastic[Seq[A]] = Stochastic {
    Seq.fill(n)(sample)
  }

  def repeatToTensor[T[_], U, A1 >: A](axes: U, shape: Seq[Int])(implicit T: IsTensorK[T, A1]): Stochastic[T[U]] =
    ???
    //(Array.fill(shape.product)(sample), shape.toArray)

}

object Stochastic {

  /** Creates a stochastic variable with a call-by-name parameter. */
  def apply[A](f: => A): Stochastic[A] = new Stochastic[A] {
    def sample = f
  }

  def always[A](a: A): Deterministic[A] =
    Deterministic(a)

  implicit object Monad extends Monad[Stochastic] {
    def pure[A](a: A) = always(a)
    def flatMap[A, B](fa: Stochastic[A])(f: A => Stochastic[B]) = fa.flatMap(f)
    override def map[A, B](fa: Stochastic[A])(f: A => B) = fa.map(f)
    def tailRecM[A, B](a: A)(f: A => Stochastic[Either[A, B]]) = f(a) collect { case Right(b) => b }
  }

  class InfiniteSamples[A](self: Stochastic[A]) extends AbstractIterable[A] {
    def iterator = new AbstractIterator[A] {
      def hasNext = true
      def next() = self.sample
    }
  }

  class Conditional[A, B](self: Stochastic[A], f: A => Boolean) extends Stochastic[A] {
    def sample: A = {
      do {
        val x = self.sample
        if (f(x)) return x
      } while (true)
      throw new IllegalStateException("This should never happen.")
    }
  }

  class ConditionallyMapped[A, B](self: Stochastic[A], pf: PartialFunction[A, B]) extends Stochastic[B] {
    val f = pf.lift
    def sample: B = {
      do {
        val x = self.sample
        f(x) match {
          case Some(y) => return y
          case None =>
        }
      } while (true)
      throw new IllegalStateException("This should never happen.")
    }
  }

}
