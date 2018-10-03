package nexus.tensor.syntax

/**
 * @author Tongfei Chen
 */
trait TensorSyntax {

  implicit class VectorOps[T[_], E, A <: Dim](a: T[A])(implicit T: IsTensorK[T, E]) {
    def apply(i: Int): E = T.get(a, Array(i))
  }

}
