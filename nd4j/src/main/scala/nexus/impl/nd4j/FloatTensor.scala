package nexus.impl.nd4j

import nexus.algebra.typelevel._
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import shapeless._
import shapeless.ops.nat._



/**
  * A tensor stored off-heap,
  * and whose operations run either on the CPU or the GPU depening on Nd4j backend.
  * @author Andrey Romanov
  * @since 0.1.0
  */
trait FloatTensor[A] extends UntypedFloatTensor { self =>

  protected def slice0[N <: Nat, T]
  (axis: N, i: Int)
  (implicit t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): FloatTensor[T] =
    sliceUntyped(nn(), i).typeWith[T]


  def sliceAlong[X, N <: Nat, T]
  (axis: X, i: Int)
  (implicit n: IndexOf.Aux[A, X, N], t: RemoveAt.Aux[A, N, T], nn: ToInt[N]) = slice0(n(), i)

  def along[X, N <: Nat, T]
  (axis: X)
  (implicit n: IndexOf.Aux[A, X, N], t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): Seq[FloatTensor[T]] =
    (0 until shape(nn())) map { i => slice0(n(), i) }

  def expandDim[X, N <: Nat, T]
  (axis: X, n: N)
  (implicit d: InsertAt.Aux[A, N, X, T], nn: ToInt[N]) = ???


  def asSeq: Seq[Float] = ???

}

object FloatTensor {
  private def convertShape(shape: Array[Int]) = shape match {
    case Array() => Array(1, 1)
    case Array(d) => Array(d, 1)
    case s => s
  }

  def scalar(value: Float): FloatTensor[Unit] =
    new Tensor(Nd4j.scalar(value))

  def scalar(value: Double): FloatTensor[Unit] =
    new Tensor(Nd4j.scalar(value))

  def fill[A](value: => Float, axes: A, shape: Array[Int]): FloatTensor[A] =
    new Tensor(Nd4j.ones(convertShape(shape): _*).mul(value))

  def fromFlatArray[A](array: Array[Float], shape: Array[Int]): FloatTensor[A] =
    new Tensor(Nd4j.create(array, convertShape(shape)))

  def fromNestedArray[A, N <: Nat, T]
  (axes: A)(array: T)
  (implicit nest: Nest[T, Float, N], nn: Len.Aux[A, N]) =
    fromFlatArray[A](nest.flatten(array), nest.shape(array))

  class Tensor[A](handle: INDArray)
    extends UntypedFloatTensor.Tensor(handle) with FloatTensor[A]
}

object Scalar {
  def apply(x: Float) = FloatTensor.fromFlatArray[Unit](Array(x), Array())
}

object Vector {
  def apply[A](A: A)(array: Array[Float]) = FloatTensor.fromFlatArray[A](array, Array(array.length))
}
