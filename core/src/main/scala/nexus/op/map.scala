package nexus.op

import nexus._
import nexus.algebra._
import scala.annotation._

/**
 * Applies an arbitrary differentiable function to all elements in a specific tensor.
 * @note This operation might be slow! Use with caution.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Map[X](parameter: DOp1[X, X]) extends ParaPolyDOp1[DOp1[X, X]] {

  type F[P, X, Y] = Map.F[P, X, Y]

}

object Map {
  @implicitNotFound("Cannot apply Map[${P}] to ${X}.")
  trait F[O, X, Y] extends (O => DOp1[X, Y])

  object F {

    implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsRealTensor[T, R]) = new F[DOp1[R, R], T[A], T[A]] {
      def apply(f: DOp1[R, R]) = new DOp1[T[A], T[A]] {
        import T._
        def name = s"Map[${f.name}]"
        def tag = T.ground[A]
        def forward(x: T[A]) = map(x)(f.forward)
        def backward(dy: T[A], y: T[A], x: T[A]) = map3(dy, y, x)(f.backward)
      }
    }

  }

}
