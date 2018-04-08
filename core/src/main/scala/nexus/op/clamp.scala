package nexus.op

import nexus._
import nexus.algebra._

/**
 * @author Tongfei Chen
 */
object Clamp extends ParameterizedPolyOp1 {

  implicit def clampF[T[_], R, A](implicit T: IsRealTensorK[T, R]) = (range: (R, R)) =>
      new F[T[A], T[A]] {
        val (min, max) = range
        def name = s"Clamp[$min, $max]"
        def tag(tx: Type[T[A]]) = tx
        def forward(x: T[A]) = ???
        def backward(dy: T[A], y: T[A], x: T[A]) = ???
      }

}

