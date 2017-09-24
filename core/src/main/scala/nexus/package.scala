import nexus.algebra._
import nexus.algebra.{instances => nxai}
import nexus.syntax._
import shapeless._

/**
 * '''Nexus''': A typesafe tensor / deep learning library.
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
  def Float16 = ???

  type         Float32 = Float
  implicit val Float32 = nxai.Float32

  type         Float64 = Double
  implicit val Float64 = nxai.Float64

  type         Bool    = Boolean
  implicit val Bool    = nxai.Bool

}
