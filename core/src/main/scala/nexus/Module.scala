package nexus

/**
 * @author Tongfei Chen
 */
trait Module1[X, Y] extends Func1[X, Y] with ModuleBase { self =>

  def >>[Z](that: Func1[Y, Z]): Module1[X, Z] = new Module1[X, Z] {

    def parameters = self.parameters ++ (that match {
      case that: Module1[Y, Z] => that.parameters
      case _ => Set()
    })

    def apply(x: Expr[X]) = that(self(x))

  }

  def >>[Z](that: PolyFunc1)(implicit f: that.F[Y, Z]): Module1[X, Z] = self >> that.ground(f)


}

trait Module2[X1, X2, Y] extends Func2[X1, X2, Y] with ModuleBase

trait Module3[X1, X2, X3, Y] extends Func3[X1, X2, X3, Y] with ModuleBase

trait ModuleBase {

  def parameters: Set[Param[_]]

}
