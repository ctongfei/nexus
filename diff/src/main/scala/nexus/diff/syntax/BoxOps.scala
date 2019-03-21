package nexus.diff.syntax

import cats._
import nexus.IsReal
import nexus.diff._
import nexus.diff.execution.Forward

/**
 * @author Tongfei Chen
 */
trait BoxOpsMixin {

  implicit class TaggedBoxOps[D[_], X](x: D[X])(implicit D: DifferentiableAlgebra[D]) {

    def tag = D.tag(x)

  }

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

  implicit class LossOps[D[_], R: IsReal](x: D[R])(implicit D: DifferentiableAlgebra[D]) {

    /**
     * Computes the gradients of all parameters from this loss.
     */
    def gradients(implicit F: Forward[D]) = D.gradients(x)
  }

}
