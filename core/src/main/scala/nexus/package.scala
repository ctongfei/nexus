import shapeless._

/**
 * @author Tongfei Chen
 */
package object nexus extends
  TensorOpsMixin with
  ExprTensorMixin with
  Tuple2ExprOpsMixin
{

  private[nexus] type ::[+H, +T <: HList] = shapeless.::[H, T]
  private[nexus] type $$ = HList
  private[nexus] type $ = HNil
  private[nexus] val  $: $ = HNil // explicit type annotation to avoid some implicit search bugs

  private[nexus] type impMsg = scala.annotation.implicitNotFound

  private[nexus] type Field[A] = algebra.ring.Field[A]
  private[nexus] type Ring[A]  = algebra.ring.Ring[A]

}

