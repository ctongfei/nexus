package nexus.diff

import shapeless.{HList, Nat}

/**
 * Base trait for all modules.
 * Modules serve as basic building blocks of neural network that may contain parameters.
 *
 * A module should always be implemented as an instance of the trait `Product`, preferably as a '''case class''':
 * This enables recursive traversal over its components to get all the parameters.
 */
trait AnyModule extends HasParameters { self: Product =>

  /**
   * Returns the set of all parameters in this module.
   */
  lazy val parameters: Set[Param[_]] = {
    val paramsOfSubmodules: Iterator[Set[Param[_]]] = this.productIterator.map {
      case p: Param[_] => Set(p)
      case p: AnyModule => p.parameters
      case _ => Set()
    }
    paramsOfSubmodules.reduce(_ union _)
  }

  def parameterMap: Map[String, Param[_]] =
    parameters.view.map(p => p.name -> p).toMap

  def loadFromParameterMap(m: Map[String, Param[_]]): Unit =
    for (p <- parameters)
      p.assign_!(m(p.name).value)

}

trait Module0[Y] extends Func0[Y] with AnyModule { self: Product => }

trait Module1[X, Y] extends Func1[X, Y] with AnyModule { self: Product =>

  def >>[Z](that: Module1[Y, Z]): Module1[X, Z] = Module1.Composed(this, that)
  def >>[Z](that: PolyModule1)(implicit p: that.P[Y, Z]): Module1[X, Z] = self >> that.ground(p)

}

object Module1 {
  case class Composed[X, Y, Z](f: Module1[X, Y], g: Module1[Y, Z]) extends Module1[X, Z] {
    def apply[F[_]: Algebra](x: F[X]): F[Z] = g(f(x))
  }
}

trait Module2[X1, X2, Y] extends Func2[X1, X2, Y] with AnyModule { self: Product => }

trait Module3[X1, X2, X3, Y] extends Func3[X1, X2, X3, Y] with AnyModule { self: Product => }
