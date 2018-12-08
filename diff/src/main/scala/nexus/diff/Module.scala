package nexus.diff

import nexus.diff.execution._


trait AnyModule extends HasParameters {

  def parameters: Set[Param[_]]

  def parameterMap =
    parameters.view.map(p => p.name -> p).toMap

  def loadFromParameterMap(m: Map[String, Param[_]]): Unit =
    for (p <- parameters)
      p.__assign_!(m(p.name).value)

}

trait Module1[X, Y] extends Func1[X, Y] with AnyModule { self =>

  def >>[Z](that: Func1[Y, Z]): Module1[X, Z] = new Module1[X, Z] {

    def parameters = self.parameters union (that match {
      case that: Module1[Y, Z] => that.parameters
      case _ => Set()
    })

    def apply(x: Symbolic[X]) = that(self(x))

  }

  def >>[Z](that: PolyFunc1)(implicit f: that.F[Y, Z]): Module1[X, Z] = self >> that.ground(f)

}

trait Module2[X1, X2, Y] extends Func2[X1, X2, Y] with AnyModule

trait Module3[X1, X2, X3, Y] extends Func3[X1, X2, X3, Y] with AnyModule
