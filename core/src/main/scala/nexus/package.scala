import nexus.algebra._
import shapeless._

/**
 * '''Nexus''': A typesafe tensor / deep learning library
 * @author Tongfei Chen
 */
package object nexus extends ExprTensorMixin {

  type ::[+H, +T <: HList] = shapeless.::[H, T]
  type $$ = HList
  type $ = HNil
  val  $: $ = HNil // explicit type annotation to avoid some implicit search bugs

  implicit val Float32 = RealOps.Float32
  implicit val Float64 = RealOps.Float64

}
