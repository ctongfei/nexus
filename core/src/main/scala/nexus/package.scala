import nexus.algebra._
import nexus.algebra.{instances => nxai}
import nexus.syntax._

/**
 * '''Nexus''': Typeful & typesafe deep learning.
 * @author Tongfei Chen
 */
package object nexus extends ExprRealTensorMixin with ExprRealMixin with TupleExprOpsMixin {
  
  val _0 = shapeless.nat._0
  val _1 = shapeless.nat._1
  val _2 = shapeless.nat._2
  val _3 = shapeless.nat._3
  val _4 = shapeless.nat._4
  val _5 = shapeless.nat._5
  val _6 = shapeless.nat._6
  val _7 = shapeless.nat._7
  val _8 = shapeless.nat._8
  val _9 = shapeless.nat._9

  // ALIASES AND TYPE TAGS FOR BASIC TYPES
  type         Int8    = Byte
  implicit val Int8    = nxai.Int8

  type         Int16   = Short
  implicit val Int16   = nxai.Int16

  type         Int32   = Int
  implicit val Int32   = nxai.Int32

  type         Int64   = Long
  implicit val Int64   = nxai.Int64

  type         Float16 = Half
  implicit val Float16 = nxai.Float16

  type         Float32 = Float
  implicit val Float32 = nxai.Float32

  type         Float64 = Double
  implicit val Float64 = nxai.Float64

  type         Bool    = Boolean
  implicit val Bool    = nxai.Bool

  type Func1[X, Y]                  = (Expr[X] => Expr[Y])
  type Func2[X1, X2, Y]             = ((Expr[X1], Expr[X2]) => Expr[Y])
  type Func3[X1, X2, X3, Y]         = ((Expr[X1], Expr[X2], Expr[X3]) => Expr[Y])
  type Func4[X1, X2, X3, X4, Y]     = ((Expr[X1], Expr[X2], Expr[X3], Expr[X4]) => Expr[Y])
  type Func5[X1, X2, X3, X4, X5, Y] = ((Expr[X1], Expr[X2], Expr[X3], Expr[X4], Expr[X5]) => Expr[Y])

  trait Module1[X, Y] extends (Expr[X] => Expr[Y]) {
    def parameters: Set[Any]
  }

  trait Module2[X1, X2, Y] extends ((Expr[X1], Expr[X2]) => Expr[Y]) {
    def parameters: Set[Any]
  }

  trait Module3[X1, X2, X3, Y] extends ((Expr[X1], Expr[X2], Expr[X3]) => Expr[Y]) {
    def parameters: Set[Any]
  }

  private[nexus] def typeName(o: Any) = {
    val raw = o.getClass.getTypeName
    val last = raw.split('.').last
    if (last endsWith "$") last.init else last
  }

}
