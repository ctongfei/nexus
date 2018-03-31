package nexus.impl.nd4j

import nexus.algebra.typelevel._
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import org.nd4s.Implicits._
import shapeless._
import shapeless.ops.nat._



/**
  * A tensor stored off-heap,
  * and whose operations run either on the CPU or the GPU depening on Nd4j backend.
  * @author Andrey Romanov
  * @since 0.1.0
  */
trait ND4JTensor[A] extends UntypedND4JTensor { self =>

  //def update(indices: Int*)(newValue: Float) = handle(index(indices)) = newValue

  protected def slice0[N <: Nat, T]
  (axis: N, i: Int)
  (implicit t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): ND4JTensor[T] =
    sliceUntyped(nn(), i).typeWith[T]


  def sliceAlong[X, N <: Nat, T]
  (axis: X, i: Int)
  (implicit n: IndexOf.Aux[A, X, N], t: RemoveAt.Aux[A, N, T], nn: ToInt[N]) = slice0(n(), i)

  def along[X, N <: Nat, T]
  (axis: X)
  (implicit n: IndexOf.Aux[A, X, N], t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): Seq[ND4JTensor[T]] =
    (0 until shape(nn())) map { i => slice0(n(), i) }

  def expandDim[X, N <: Nat, T]
  (axis: X, n: N)
  (implicit d: InsertAt.Aux[A, N, X, T], nn: ToInt[N]) = ???


  def asSeq: Seq[Float] = ???

}

object ND4JTensor {

  def scalar(value: Float): ND4JTensor[Unit] =
    new Tensor(Nd4j.scalar(value))

  def scalar(value: Double): ND4JTensor[Unit] =
    new Tensor(Nd4j.scalar(value))

  def fill[A](value: => Float, axes: A, shape: Array[Int]): ND4JTensor[A] =
    new Tensor(Nd4j.ones(shape: _*) * value)

  def fromFlatArray[A](array: Array[Float], shape: Array[Int]): ND4JTensor[A] =
    new Tensor(array.asNDArray(shape: _*))

  def fromNestedArray[A, N <: Nat, T]
  (axes: A)(array: T)
  (implicit nest: Nest[T, Float, N], nn: Len.Aux[A, N]) =
    fromFlatArray[A](nest.flatten(array), nest.shape(array))

  class Tensor[A](handle: INDArray)
    extends UntypedND4JTensor.Tensor(handle) with ND4JTensor[A]
}

object Scalar {
  def apply(x: Float) = ND4JTensor.fromFlatArray[Unit](Array(x), Array())
}

object Vector {
  def apply[A](A: A)(array: Array[Float]) = ND4JTensor.fromFlatArray[A](array, Array(array.length))
}
