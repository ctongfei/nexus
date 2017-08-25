package nexus

import shapeless._

/**
 * Contains basic typeclass definitions for tensor operations.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
package object algebra {

  private[algebra] type ::[+H, +T <: HList] = shapeless.::[H, T]
  private[algebra] type $$ = HList
  private[algebra] type $ = HNil
  private[algebra] val  $: $ = HNil // explicit type annotation to avoid some implicit search bugs

}
