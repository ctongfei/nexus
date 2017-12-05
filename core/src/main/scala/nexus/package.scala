import nexus.algebra._
import nexus.algebra.{instances => nxai}
import nexus.syntax._
import shapeless._

/**
 * '''Nexus''': Typeful & typesafe deep learning.
 * @author Tongfei Chen
 */
package object nexus extends ExprTensorMixin {

  type ::[+H, +T <: HList] = shapeless.::[H, T]
  type $$ = HList
  type $ = HNil
  val  $: $ = HNil // explicit type annotation to avoid some implicit search bugs

  type         Int8    = Byte
  implicit val Int8    = nxai.Int8

  type         Int16   = Short
  implicit val Int16   = nxai.Int16

  type         Int32   = Int
  implicit val Int32   = nxai.Int32

  type         Int64   = Long
  implicit val Int64   = nxai.Int64

  type         Float16 = Half
  //TODO: Float16

  type         Float32 = Float
  implicit val Float32 = nxai.Float32

  type         Float64 = Double
  implicit val Float64 = nxai.Float64

  type         Bool    = Boolean
  implicit val Bool    = nxai.Bool

  type Module [X, Y]                  = (Expr[X] => Expr[Y])
  type Module2[X1, X2, Y]             = ((Expr[X1], Expr[X2]) => Expr[Y])
  type Module3[X1, X2, X3, Y]         = ((Expr[X1], Expr[X2], Expr[X3]) => Expr[Y])
  type Module4[X1, X2, X3, X4, Y]     = ((Expr[X1], Expr[X2], Expr[X3], Expr[X4]) => Expr[Y])
  type Module5[X1, X2, X3, X4, X5, Y] = ((Expr[X1], Expr[X2], Expr[X3], Expr[X4], Expr[X5]) => Expr[Y])

  type DModule[X, Y]           = (Expr[X] => DExpr[Y])
  type DModule2[X1, X2, Y]     = ((Expr[X1], Expr[X2]) => DExpr[Y])
  type DModule3[X1, X2, X3, Y] = ((Expr[X1], Expr[X2], Expr[X3]) => DExpr[Y])


  private[nexus] def objTypeName(a: Any) = {
    val raw = a.getClass.getTypeName
    val last = raw.split('.').last
    if (last.endsWith("$")) last.init else last
  }

}
