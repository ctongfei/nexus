package nexus.layer

import nexus._

/**
 * @author Tongfei Chen
 */
abstract class Attention[M, X, T[_, _ <: $$], D] extends Module2[T[D, M::X::$], T[D, X::$], T[D, M::$]] {

  def similarity: (Expr[M], Expr[X]) => Expr[T[D, $]]

  def apply(memory: Expr[Seq[M]], x: Expr[X]): Expr[M] = { ???
    // memory.along(M).map(m => similarity(m, x)).asVector(A).softmax.
  }

}
