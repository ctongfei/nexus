package nexus.algebra

/**
 * @author Tongfei Chen
 */

trait IsAxisGroundedTensor[T[_], E, A] extends IsTensor[T[A], E] {

  def parent: IsTensorK[T, E]

}

trait IsSizeKnownTensor[T[_], E, A] extends IsAxisGroundedTensor[T, E, A] {
  def size: Array[Int]
  def rank = size.length
}


trait IsAxisGroundedRealTensor[T[_], R, A] extends IsAxisGroundedTensor[T, R, A] with Grad[T[A]] {
  def parent: IsRealTensorK[T, R]

  def zeroBy(x: T[A]) = parent.zeroBy(x)
  def add(x1: T[A], x2: T[A]) = parent.add(x1, x2)
  def addS(x1: T[A], x2: Double) = parent.addS(x1, parent.R.fromDouble(x2))
  def addI(x1: T[A], x2: T[A]): Unit = parent.addI(x1, x2)
  def sub(x1: T[A], x2: T[A]) = parent.sub(x1, x2)
  def neg(x: T[A]) = parent.neg(x)
  def eMul(x1: T[A], x2: T[A]) = parent.eMul(x1, x2)
  def eDiv(x1: T[A], x2: T[A]) = parent.eDiv(x1, x2)
  def scale(x: T[A], k: Double) = parent.scale(x, parent.R.fromDouble(k))
  def eSqrt(x: T[A]) = parent.eSqrt(x)
}

trait IsSizeKnownRealTensor[T[_], R, A] extends IsAxisGroundedRealTensor[T, R, A] with IsSizeKnownTensor[T, R, A]

