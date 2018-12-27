package nexus.diff.ops

import nexus.diff._
import nexus._

/**
 * @author Tongfei Chen
 */
object Clamp extends ParameterizedPolyOp1 {

  implicit def clampF[T[_], R, a](implicit T: IsRealTensorK[T, R]) = (range: (R, R)) =>
      new F[T[a], T[a]] {
        val (min, max) = range
        def name = s"Clamp[$min, $max]"
        def tag = Tag.realTensor[T, R, a]
        def forward(x: T[a]) = ???
        def backward(dy: T[a], y: T[a], x: T[a]) = ???
      }

}

