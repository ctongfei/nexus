package nexus.layer.recurrent

import nexus._
import nexus.op._

/**
 * @author Tongfei Chen
 */
class SimpleRecurrentUnit[T[_, _ <: $$], D, H, I] extends RecurrentUnit[T, D, H, I] {

  def apply(h: Expr[T[D, H::$]], s: Expr[T[D, I::$]]) = ???

}
