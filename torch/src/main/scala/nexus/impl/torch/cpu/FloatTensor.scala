package nexus.impl.torch.cpu

import jtorch._
import nexus._
import nexus.algebra._
import nexus.algebra.typelevel.util._

//
//object FloatTensor extends IsRealTensorK[FloatTensor, Float] {
//
//  type H = UntypedFloatTensor
//  val H = UntypedFloatTensor
//
//  implicit val R = Float32
//
//  def tabulate[A](shape: Array[Int])(f: Array[Int] => Int) = ???
//  def set(x: FloatTensor[_], is: Array[Int], v: Float): Unit = ???
//
//  def newDim0Tensor(x: Float): FloatTensor[Unit] = new FloatTensor[Unit](UntypedFloatTensor.Dim0(x))
//
//  def fill[A](value: => Float, axes: A, shape: Array[Int]) = ???
//
//  def untype(x: FloatTensor[_]) = x.untyped
//  def typeWith[A](x: UntypedFloatTensor) = new FloatTensor[A](x)
//
//  def zeroBy[A](x: FloatTensor[A]) = typeWith[A](UntypedFloatTensor.zeroBy(untype(x)))
//
//  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]) = {
//    val r = new scala.util.Random()
//    val σ = math.sqrt(σ2)
//    FloatTensor.fromFlatArray(Array.fill(ShapeUtils.product(shape))(((r.nextGaussian() - μ) * σ).toFloat), shape)
//  }
//
//  def newTensor[A](shape: Array[Int]) = ???
//
//}
