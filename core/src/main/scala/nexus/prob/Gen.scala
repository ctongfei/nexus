package nexus.prob

import cats._
import scala.collection._
import scala.util._

/**
 * A monadic sampler.
 * @author Tongfei Chen
 */
trait Gen[+A] { self =>

  def sample: A

  def samples: Iterable[A] = new AbstractIterable[A] {
    def iterator = new AbstractIterator[A] {
      def hasNext = true
      def next() = sample
    }
  }

  def map[B](f: A => B): Gen[B] = Gen.of(f(sample))

  def flatMap[B](f: A => Gen[B]): Gen[B] = Gen.of(f(sample).sample)

  def filter(f: A => Boolean): Gen[A] = new Gen.Conditional(self, f)

  def collect[B](pf: PartialFunction[A, B]): Gen[B] = new Gen.ConditionallyMapped(self, pf)

  def product[B](that: Gen[B]): Gen[(A, B)] = Gen.of(self.sample, that.sample)

  def productWith[B, C](that: Gen[B])(f: (A, B) => C): Gen[C] = Gen.of(f(self.sample, that.sample))

  def seq(n: Int): Gen[Seq[A]] = Gen.of(Seq.fill(n)(sample))

}

object Gen {

  def of[A](f: => A): Gen[A] = new AbstractGen[A] {
    def sample = f
  }

  implicit object Monad extends Monad[Gen] {
    def pure[A](x: A) = of(x)
    def flatMap[A, B](fa: Gen[A])(f: A => Gen[B]) = fa.flatMap(f)
    override def map[A, B](fa: Gen[A])(f: A => B) = fa.map(f)
    def tailRecM[A, B](a: A)(f: A => Gen[Either[A, B]]) = f(a) collect { case Right(b) => b }
  }

  class Conditional[A, B](self: Gen[A], f: A => Boolean) extends AbstractGen[A] {
    def sample: A = {
      do {
        val x = self.sample
        if (f(x)) return x
      } while (true)
      null.asInstanceOf[A] // will never happen
    }
  }

  class ConditionallyMapped[A, B](self: Gen[A], pf: PartialFunction[A, B]) extends AbstractGen[B] {
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

abstract class AbstractGen[+A] extends Gen[A]
