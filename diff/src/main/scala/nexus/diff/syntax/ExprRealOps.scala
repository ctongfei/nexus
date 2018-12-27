package nexus.diff.syntax

import nexus.diff._
import nexus.diff.ops._
import nexus._

/**
 * @author Tongfei Chen
 */
trait SymbolicRealOpsMixin {

  implicit class SymbolicRealOps[R](val x: Symbolic[R])(implicit R: IsReal[R]) {

    def +(y: Symbolic[R]): Symbolic[R] = Add(x, y)

    def -(y: Symbolic[R]): Symbolic[R] = Sub(x, y)

    def *(y: Symbolic[R]): Symbolic[R] = Mul(x, y)

    def /(y: Symbolic[R]): Symbolic[R] = Div(x, y)

  }


  implicit class ExprRealOps2[T[_], R, A](val x: Symbolic[R])(implicit T: IsRealTensorK[T, R]) {

    def *:(y: Symbolic[T[A]]): Symbolic[T[A]] = Scale(x, y)

  }

}
