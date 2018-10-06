package nexus

import nexus.tensor._

/**
 * Type tag containing an evidence typeclass instance on a variable.
 * This is used in backpropagation to know what operations are supported on a specific expression.
 * @author Tongfei Chen
 * @since 0.1.0
 */
sealed trait Tag[X] {

  type Ev[_]
  val ev: Ev[X]

}

object Tag {

  def apply[X, T[_]](implicit t: Tag[X]): Aux[X, t.Ev] = t
  type Aux[X, T[_]] = Tag[X] { type Ev[x] = T[x] }

  private class Scalar[X, Evidence[_]](evidence: Evidence[X]) extends Tag[X] {
    type Ev[x] = Evidence[x]
    val ev = evidence
  }

  private class Tensor[T[_], E, a, EvidenceK[t[_], e] <: IsTensorK[t, e]](val evK: EvidenceK[T, E]) extends Tag[T[a]] {
    type Ev[ta] = evK.TensorTag[ta]
    val ev = evK.ground[a]
  }

  def of[T[_], X](tag: T[X]): Tag.Aux[X, T] = new Scalar[X, T](tag)

  def any[X]: Tag[X] = new Tag[X] {
    type Ev[x] = Unit
    val ev: Unit = ()
  }

  def bool[B](implicit B: IsBool[B]): Tag.Aux[B, IsBool] = new Scalar[B, IsBool](B)
  def int[Z](implicit Z: IsInt[Z]): Tag.Aux[Z, IsInt] = new Scalar[Z, IsInt](Z)
  def real[R](implicit R: IsReal[R]): Tag.Aux[R, IsReal] = new Scalar[R, IsReal](R)

  //def boolTensor[T[_], B, a](implicit T: IsBoolTensorK[T, B]): Tag.Aux[T[a], ({type L[ta] = IsTensor[ta, B]})#L] =
  //  new Tensor[T, B, a, IsBoolTensorK](T)
  //def realTensor[T[_], Z, a](implicit T: IsIntTensorK[T, Z]): Tag.Aux[T[a], ({type L[ta] = IsRealTensor[ta, Z]})#L] =
  //  new Tensor[T, R, a, IsRealTensorK](T)
  def tensor[T[_], E, a](implicit T: IsTensorK[T, E]): Tag.Aux[T[a], ({type L[ta] = IsTensor[ta, E]})#L] = new Tag[T[a]] {
    type Ev[ta] = IsTensor[ta, E]
    val ev = T.ground[a]
  }

  def realTensor[T[_], R, a](implicit T: IsRealTensorK[T, R]): Tag.Aux[T[a], ({type L[ta] = IsRealTensor[ta, R]})#L] =
    new Tensor[T, R, a, IsRealTensorK](T)

}
