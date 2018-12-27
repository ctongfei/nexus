package nexus.diff.syntax

import nexus._

/**
 * @author Tongfei Chen
 */
trait BoolMixin {

  implicit class BoolOps[B](val a: B)(implicit B: IsBool[B]) {

    def &&(b: B) = B.and(a, b)
    def ||(b: B) = B.or(a, b)
    def unary_! = B.not(a)

    def ^(b: B) = B.xor(a, b)

  }

  implicit class CondOps[B, E[_]](val c: B)(implicit B: Cond[B, E]) {

    def cond[A](t: E[A], f: E[A]): E[A] = B.cond(c, t, f)

  }

}
