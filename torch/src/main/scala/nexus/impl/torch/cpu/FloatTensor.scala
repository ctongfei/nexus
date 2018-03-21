package nexus.impl.torch.cpu

import nexus._
import jtorch.cpu._
import nexus.algebra._
import nexus.algebra.typelevel.util._

/**
 * 32-bit floating point dense tensor, implemented in Torch.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class FloatTensor[A](val untyped: UntypedFloatTensor) {

  import UntypedFloatTensor._

  def rank = FloatTensor.rank(this)

  def shape = FloatTensor.shape(this)

  def offset = untyped.offset
  def strides = untyped.strides

  def apply(indices: Int*) = untyped match {
    case Dim0(x) if indices.length == 0 => x
    case Dense(x) =>
      indices.length match {
        case 0 => ???
        case 1 => TH.THFloatTensor_get1d(x, indices(0))
        case 2 => TH.THFloatTensor_get2d(x, indices(0), indices(1))
        case 3 => TH.THFloatTensor_get3d(x, indices(0), indices(1), indices(2))
        case 4 => TH.THFloatTensor_get4d(x, indices(0), indices(1), indices(2), indices(3))
        case _ => ???
      }
  }

  override def toString = untyped.stringRepr



}

object FloatTensor extends IsRealTensorK[FloatTensor, Float] {

  type H = UntypedFloatTensor
  val H = UntypedFloatTensor

  implicit val R = Float32

  def get(x: FloatTensor[_], is: Array[Int]) = ???
  def set(x: FloatTensor[_], is: Array[Int], v: Float): Unit = ???

  def newDim0Tensor(x: Float): FloatTensor[Unit] = new FloatTensor[Unit](UntypedFloatTensor.Dim0(x))

  def fill[A](value: => Float, axes: A, shape: Array[Int]) = ???

  def untype(x: FloatTensor[_]) = x.untyped
  def typeWith[A](x: UntypedFloatTensor) = new FloatTensor[A](x)

  def zeroBy[A](x: FloatTensor[A]) = typeWith[A](UntypedFloatTensor.zeroBy(untype(x)))

  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]) = {
    val r = new scala.util.Random()
    val σ = math.sqrt(σ2)
    FloatTensor.fromFlatArray(Array.fill(ShapeUtils.product(shape))(((r.nextGaussian() - μ) * σ).toFloat), shape)
  }

  def newTensor[A](shape: Array[Int]) = ???

}
