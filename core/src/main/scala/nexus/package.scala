import nexus.algebra._
import nexus.syntax._
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

}
