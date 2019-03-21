package nexus

class IndexTensor[T[_], Z, U, I <: Dim] private(val underlying: T[U])

object IndexTensor {

  def apply[T[_], Z, U, I <: Dim](underlying: T[U], dim: I)(implicit T: IsIntTensorK[T, Z]) =
    new IndexTensor[T, Z, U, I](underlying)

}
