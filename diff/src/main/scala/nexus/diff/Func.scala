package nexus.diff

import nexus._
import cats.arrow._

trait Func0[Y] {
  def apply[F[_]: Algebra](): F[Y]
}

trait Func1[X, Y] extends (X => Y) {
  def apply[F[_]: Algebra](x: F[X]): F[Y]
  def apply(x: X): Y = apply[Id](x)
}

trait Func2[X1, X2, Y] extends ((X1, X2) => Y) {
  def apply[F[_]: Algebra](x1: F[X1], x2: F[X2]): F[Y]
  def apply(x1: X1, x2: X2): Y = apply[Id](x1, x2)
}

trait Func3[X1, X2, X3, Y] extends ((X1, X2, X3) => Y) {
  def apply[F[_]: Algebra](x1: F[X1], x2: F[X2], x3: F[X3]): F[Y]
  def apply(x1: X1, x2: X2, x3: X3) = apply[Id](x1, x2, x3)
}


object Func1 {

  implicit object Category extends Category[Func1] {
    def id[A] = new Func1[A, A] {
      def apply[F[_]: Algebra](x: F[A]) = x
    }
    def compose[A, B, C](f: Func1[B, C], g: Func1[A, B]) = new Func1[A, C] {
      def apply[F[_]: Algebra](x: F[A]) = f(g(x))
    }
  }

}
