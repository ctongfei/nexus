package nexus.diff.ops

import nexus.diff._
import nexus._

/**
 * @author Tongfei Chen
 */
object Clamp extends ParameterizedPolyOp1 {

  implicit def clampF[T[_], R, I](implicit T: IsRealTensorK[T, R]) = (range: (R, R)) =>
      new F[T[I], T[I]] {
        val (min, max) = range
        def name = s"Clamp[$min, $max]"
        def tag = Tag.realTensor[T, R, I]
        def forward(x: T[I]) = ???
        def backward(dy: T[I], y: T[I], x: T[I]) = ???
      }

}

