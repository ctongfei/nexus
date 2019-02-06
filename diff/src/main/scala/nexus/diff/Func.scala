package nexus.diff

import cats.arrow._

/**
 * @author Tongfei Chen
 */
trait Func0[Y] {
  def apply[D[_]: Algebra](): D[Y]
}

trait Func1[X, Y] {
  def apply[D[_]: Algebra](x: D[X]): D[Y]
}

object Func1 {

  implicit object Category extends Category[Func1] {
    def id[A] = new Func1[A, A] {
      def apply[D[_]: Algebra](x: D[A]) = x
    }
    def compose[A, B, C](f: Func1[B, C], g: Func1[A, B]) = new Func1[A, C] {
      def apply[D[_]: Algebra](x: D[A]) = f(g(x))
    }
  }

}

trait Func2[X1, X2, Y] {
  def apply[D[_]: Algebra](x1: D[X1], x2: D[X2]): D[Y]
}

trait Func3[X1, X2, X3, Y] {
  def apply[D[_]: Algebra](x1: D[X1], x2: D[X2], x3: D[X3]): D[Y]
}
