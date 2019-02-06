package nexus.diff.syntax

import cats._
import nexus.diff._

/**
 * @author Tongfei Chen
 */
trait BoxOpsMixin {

  implicit class BoxOps[D[_]: Algebra, X](x: D[X]) {

    def !>[Y](f: X => Y): D[Y] = Op1.fromFunction(f)(x)

    /** Passes this expression through a function. */
    def |>[Y](f: Func1[X, Y]): D[Y] = f(x)

    /** Passes this expression through a type-polymorphic function. */
    def |>[Y](f: PolyFunc1)(implicit ff: f.F[X, Y]): D[Y] = f(x)

    /**
     * Gets the value of this expression given an implicit computation instance.
     * This may trigger the execution of computations depending on box type [[D]].
     */
    def value(implicit comp: D ~> Id): X = comp(x)
  }

}
