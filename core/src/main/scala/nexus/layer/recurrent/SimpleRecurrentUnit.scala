package nexus.layer.recurrent

import nexus._
import nexus.op._

/**
 * @author Tongfei Chen
 */
class SimpleRecurrentUnit[T[_, _ <: $$], D, H, I] extends Module2[T[D, H::$], T[D, I::$], T[D, H::$]] {

  def parameters = ???

  def apply(v1: Expr[T[D, H::$]], v2: Expr[T[D, I::$]]) = ???

}
