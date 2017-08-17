import shapeless._

/**
 * @author Tongfei Chen
 */
package object nexus extends
  TensorOpsMixin with
  ExprTensorMixin with
  TupleExprOpsMixin
{

  private[nexus] type ::[+H, +T <: HList] = shapeless.::[H, T]
  private[nexus] type $$ = HList
  private[nexus] type $ = HNil
  private[nexus] val  $: $ = HNil // explicit type annotation to avoid some implicit search bugs

  private[nexus] type Scalar[T[_ <: $$]] = T[$]
  private[nexus] type Vector[T[_ <: $$], A] = T[A::$]
  private[nexus] type Matrix[T[_ <: $$], A, B] = T[A::B::$]

  private[nexus] type Field[A] = algebra.ring.Field[A]
  private[nexus] type Ring[A]  = algebra.ring.Ring[A]

  private[nexus] type implicitNotFound = scala.annotation.implicitNotFound

}

