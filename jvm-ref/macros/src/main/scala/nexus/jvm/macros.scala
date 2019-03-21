package nexus.jvm

import scala.reflect.macros._

object macros {

  def elementwise1(c: Context)(x: c.Tree, y: c.Tree): c.Tree = {
    import c.universe._
    ???
  }

  def elementwise2(c: Context)(x: c.Tree, y: c.Tree, f: c.Tree): c.Tree = {
    import c.universe._
    ???
  }

  def reduceAll(c: Context)(x: c.Tree): c.Tree = {
    import c.universe._
    ???
  }

}
