package nexus

/**
 * Higher-order operations on symbolic expressions of sequences.
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class ExprSeqOps[A](val as: Expr[Seq[A]]) {

  def asSeq: Seq[Expr[A]]

  def head: Expr[A] = asSeq.head

  def tail: Expr[Seq[A]]

  def init: Expr[Seq[A]]

  def last: Expr[A] = asSeq.last

  def map[B](f: Expr[A] => Expr[B]): Expr[Seq[B]]

  def reduce(f: (Expr[A], Expr[A]) => Expr[A]): Expr[A]

  def zipWith[B, C](bs: Expr[Seq[B]])(f: (Expr[A], Expr[B]) => Expr[C]): Expr[Seq[C]]

  def foldLeft[S](z: Expr[S])(f: (Expr[S], Expr[A]) => Expr[S]): Expr[S]

  def foldRight[S](z: Expr[S])(f: (Expr[A], Expr[S]) => Expr[S]): Expr[S]

  def scanLeft[S](z: Expr[S])(f: (Expr[S], Expr[A]) => Expr[S]): Expr[Seq[S]]

  def scanRight[S](z: Expr[S])(f: (Expr[A], Expr[S]) => Expr[S]): Expr[Seq[S]]

  def narrowSlidingWith[B](n: Int, stride: Int = 1)(f: (Expr[Seq[A]]) => Expr[B]): Expr[Seq[B]]

  def halfSlidingWith[B](n: Int, pad: A, stride: Int = 1)(f: (Expr[Seq[A]] => Expr[B])): Expr[Seq[B]]

  def wideSlidingWith[B](n: Int, pad: A, stride: Int = 1)(f: (Expr[Seq[A]]) => Expr[B]): Expr[Seq[B]]

}

trait ExprSeqOpsMixin {

  // implicit class exprSeqWithOps[A](as: Expr[Seq[A]]) extends ExprSeqOps(as)

}
