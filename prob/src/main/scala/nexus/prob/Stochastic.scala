package nexus.prob

import cats._
import nexus._
import scala.collection._
import scala.reflect._
import scala.util._

/**
 * Represents a stochastic variable that enables monadic composition of probabilistic computations.
 * @tparam X Support of this stochastic variable
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Stochastic[+X] { self =>

  import Stochastic._

  /** Draws a sample from this stochastic variable. */
  def sample: X

  /** Lazily draws infinitely many samples. */
  def samples: Iterable[X] =
    new InfiniteSampleStream(self)

  def map[Y](f: X => Y): Stochastic[Y] =
    Stochastic(f(sample))

  def flatMap[Y](f: X => Stochastic[Y]): Stochastic[Y] =
    Stochastic(f(sample).sample)

  /** Creates a conditional stochastic variable conditioned on the given predicate. */
  def filter(f: X => Boolean): Stochastic[X] =
    new Conditional(self, f)

  /** Creates a conditional stochastic variable conditioned on the given predicate. Alias of [[filter]]. */
  def given(f: X => Boolean): Stochastic[X] =
    filter(f)

  def mapFilter[Y](f: X => Option[Y]): Stochastic[Y] =
    new ConditionallyMapped(self, f)

  def collect[Y](pf: PartialFunction[X, Y]): Stochastic[Y] =
    new ConditionallyMapped(self, pf.lift)

  /**
   * Creates a joint distribution of two stochastic variables, assuming that they are independent.
   * This is the [[cats.Applicative]] `product` function on [[nexus.prob.Stochastic]].
   */
  def product[Y](that: Stochastic[Y]): Stochastic[(X, Y)] =
    Stochastic((self.sample, that.sample))

  def productWith[Y, Z](that: Stochastic[Y])(f: (X, Y) => Z): Stochastic[Z] =
    Stochastic(f(self.sample, that.sample))

  def repeatToArray[X1 >: X](n: Int)(implicit ct: ClassTag[X1]): Stochastic[Array[X1]] =
    Stochastic(Array.fill(n)(sample))

  def repeatToSeq(n: Int): Stochastic[Seq[X]] = Stochastic {
    Seq.fill(n)(sample)
  }

  def repeatToTensor[T[_], U, X1 >: X](axes: U, shape: Seq[Int])(implicit T: IsTensorK[T, X1]): Stochastic[T[U]] =
    ???
    //(Array.fill(shape.product)(sample), shape.toArray)

}

object Stochastic {

  /** Creates a stochastic variable with a call-by-name parameter. */
  def apply[X](f: => X): Stochastic[X] = new Stochastic[X] {
    def sample = f
  }

  def always[X](a: X): Deterministic[X] = Deterministic(a)

  implicit object Monad extends Monad[Stochastic] with FunctorFilter[Stochastic] {
    def pure[X](x: X) = always(x)
    def flatMap[X, Y](sx: Stochastic[X])(f: X => Stochastic[Y]) = sx.flatMap(f)
    override def map[X, Y](sx: Stochastic[X])(f: X => Y) = sx.map(f)
    override def product[X, Y](sx: Stochastic[X], sy: Stochastic[Y]) = sx.product(sy)
    override def map2[X, Y, Z](sx: Stochastic[X], sy: Stochastic[Y])(f: (X, Y) => Z) = sx.productWith(sy)(f)
    def tailRecM[X, Y](x: X)(f: X => Stochastic[Either[X, Y]]) = f(x) collect { case Right(b) => b }
    def functor = this
    def mapFilter[X, Y](sx: Stochastic[X])(f: X => Option[Y]) = sx.mapFilter(f)
    override def filter[X](sx: Stochastic[X])(f: X => Boolean) = sx.filter(f)
  }

  class InfiniteSampleStream[X](sx: Stochastic[X]) extends AbstractIterable[X] {
    def iterator = new AbstractIterator[X] {
      def hasNext = true
      def next() = sx.sample
    }
  }

  class Conditional[X, Y](sx: Stochastic[X], f: X => Boolean) extends AbstractStochastic[X] {
    def sample: X = {
      do {
        val x = sx.sample
        if (f(x)) return x
      } while (true)
      throw new IllegalStateException("This should never happen.")
    }
  }

  class ConditionallyMapped[X, Y](sx: Stochastic[X], f: X => Option[Y]) extends AbstractStochastic[Y] {
    def sample: Y = {
      do {
        val x = sx.sample
        f(x) match {
          case Some(y) => return y
          case None =>
        }
      } while (true)
      throw new IllegalStateException("This should never happen.")
    }
  }

}

abstract class AbstractStochastic[+X] extends Stochastic[X]
