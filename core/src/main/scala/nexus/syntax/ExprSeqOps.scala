package nexus.syntax

import cats.{Id, ~>}
import nexus._
import nexus.algebra._
import nexus.op.seq._

/**
 * Higher-order operations on symbolic expressions of sequences.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
class ExprSeqOps[A: Grad](as: Expr[Seq[A]]) {

  /**
   * Unrolls an expression of sequence into a sequence of expressions.
   * @note This operation forces the value of the sequence to be evaluated eagerly.
   */
  def unroll(implicit comp: Expr ~> Id): Seq[Expr[A]] = {
    val n = as.value.length
    Seq.tabulate(n)(i => Apply(as, Const(i)))
  }

}

trait ExprSeqOpsMixin {

  // implicit class exprSeqWithOps[A](as: Expr[Seq[A]]) extends ExprSeqOps(as)

}
