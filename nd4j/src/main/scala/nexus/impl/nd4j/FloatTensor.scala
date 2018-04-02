package nexus.impl.nd4j

import nexus.algebra.typelevel._
import nexus.algebra.typelevel.util.ShapeUtils
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
trait FloatTensor[A] { self =>

  val handle: INDArray
  val stride: Array[Int] = handle.stride()
  val offset: Int = handle.offset().toInt
  val shape: Array[Int] = handle.shape()

  def rank = shape.length

  def index(indices: Seq[Int]): Int = {
    var i = offset
    var k = 0
    while (k < rank) {
      i += indices(k) * stride(k)
      k += 1
    }
    i
  }

  def size = handle.length

  def apply(indices: Int*) = handle.getFloat(index(indices))

  def update(indices: Int*)(value: Float) = handle.putScalar(index(indices), value)

  def sliceUntyped(n: Int, i: Int): INDArray = handle.slice(i, n)

  def typeWith[A]: FloatTensor[A]

  def stringBody: String = handle.toString

  protected def slice0[N <: Nat, T]
  (axis: N, i: Int)
  (implicit t: RemoveAt.Aux[A, N, T], nn: ToInt[N]): FloatTensor[T] =
    new FloatTensor.Tensor[T](sliceUntyped(nn(), i))

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
    new Tensor(Nd4j.create(Array.fill(ShapeUtils.product(shape))(value), shape))

  def fromFlatArray[A](array: Array[Float], shape: Array[Int]): FloatTensor[A] =
    new Tensor(Nd4j.create(array, convertShape(shape)))

  def fromNestedArray[A, N <: Nat, T]
  (axes: A)(array: T)
  (implicit nest: Nest[T, Float, N], nn: Len.Aux[A, N]) =
    fromFlatArray[A](nest.flatten(array), nest.shape(array))

  class Tensor[A](val handle: INDArray) extends FloatTensor[A] {
    def typeWith[A] = new FloatTensor.Tensor[A](handle)
  }
}

object Scalar {
  def apply(x: Float) = FloatTensor.fromFlatArray[Unit](Array(x), Array())
}

object Vector {
  def apply[A](A: A)(array: Array[Float]) = FloatTensor.fromFlatArray[A](array, Array(array.length))
}
