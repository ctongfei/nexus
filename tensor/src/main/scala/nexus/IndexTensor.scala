package nexus

/**
 * A index tensor whose indices range in the dimension [[I]].
 * @param underlying
 * @tparam I
 * @tparam T
 * @tparam U
 */
class IndexTensor[I <: Dim, T[_], U] private(val underlying: T[U])

object IndexTensor {

  def apply[T[_], Z, U, I <: Dim](underlying: T[U], dim: I)(implicit T: IsIntTensorK[T, Z]) =
    new IndexTensor[I, T, U](underlying)

}
