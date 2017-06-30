import shapeless.HNil

/**
 * @author Tongfei Chen
 */
package object nexus extends TensorOpsMixin with ExprTensorMixin
{

  private[nexus] type $ = HNil
  private[nexus] val  $ = HNil

}

