package nexus.tensor.syntax

import nexus.tensor._

/**
 * @author Tongfei Chen
 */
trait TensorSyntax {

  implicit class VectorOps[T[_], E, a <: Dim](a: T[a])(implicit T: IsTensorK[T, E]) {
    def apply(i: Int): E = T.get(a, Array(i))
  }

}
