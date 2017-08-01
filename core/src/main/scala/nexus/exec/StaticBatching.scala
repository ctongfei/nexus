package nexus.exec

import nexus._


/**
 * @author Tongfei Chen
 */
object StaticBatching {

  def apply[X, Y](f: Input[X] => Expr[Y]) = {
    val x = Input[X]()
    val bx = Input[Seq[X]]()
    val y = f(x)
    y match {
      case _ => ???
    }
  }

}
