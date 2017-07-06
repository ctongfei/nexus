import shapeless._

/**
 * @author Tongfei Chen
 */
package object nexus extends TensorOpsMixin with ExprTensorMixin {

  implicit class ExprAssignmentOps[X](val expr: Expr[X]) extends AnyVal {

    def ->>(value: X) = Assignment(expr, value)

  }

  private[nexus] type ::[+H, +T <: HList] = shapeless.::[H, T]
  private[nexus] type $$ = HList
  private[nexus] type $ = HNil
  private[nexus] val  $ = HNil

  private[nexus] type impMsg = scala.annotation.implicitNotFound

}

