import shapeless._

/**
 * '''Nexus''': A typesafe tensor / deep learning library
 * @author Tongfei Chen
 */
package object nexus {

  type ::[+H, +T <: HList] = shapeless.::[H, T]
  type $$ = HList
  type $ = HNil
  val  $: $ = HNil // explicit type annotation to avoid some implicit search bugs

}
