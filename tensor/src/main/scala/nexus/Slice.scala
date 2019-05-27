package nexus

/**
 * A slice object used to index tensors.
 */
sealed trait Slice {

  /** The step size of this slice. */
  def step: Int

  /** Constructs a slice with a given step size. */
  def by(step: Int): Slice

  /** Transforms this slice to a bounded slice given the max size of an axis. */
  def bound(n: Int): Slice.Bounded
}

object Slice {

  sealed abstract class AbstractUnbounded(val step: Int = 1) extends Slice {
    def bound(n: Int) = Bounded(0, n, step)
    def by(step: Int) = Unbounded(step)
  }

  /** Special syntactic sugar for unbounded slices. */
  case object ? extends AbstractUnbounded(1)

  case class Unbounded(override val step: Int = 1) extends AbstractUnbounded

  case class Bounded(l: Int, r: Int, step: Int = 1) extends Slice {
    def bound(n: Int) = this
    def by(step: Int) = Bounded(l, r, step)
  }

}
