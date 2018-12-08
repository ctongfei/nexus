package nexus

import nexus.tensor._
import nexus.tensor.instances._

package object jvm {

  implicit val impFloatTensorIsRealTensorK: IsRealTensorK[FloatTensor, Float] = FloatTensor
  implicit val impDoubleTensorIsRealTensorK: IsRealTensorK[DoubleTensor, Double] = DoubleTensor

  object Float32DType extends RealDType[Float] {
    type Tensor[A] = FloatTensor[A]
    implicit val R: IsReal[Float] = FloatIsReal
    implicit val T: IsRealTensorK[FloatTensor, Float] = impFloatTensorIsRealTensorK
  }

  object Float64DType extends RealDType[Double] {
    type Tensor[A] = DoubleTensor[A]
    implicit val R: IsReal[Double] = DoubleIsReal
    implicit val T: IsRealTensorK[DoubleTensor, Double] = impDoubleTensorIsRealTensorK
  }

  // TODO: Scala typechecking bug [Fixed in Scala 2.13]
  // TODO: Aux types have to be `def` instead of `val` to typecheck
  // TODO: See https://stackoverflow.com/questions/52581986/aux-pattern-for-higher-kinded-types
  // TODO: See Scala bug #11274, #10849
  def Float32: RealDType.Aux[Float, FloatTensor] = Float32DType
  def Float64: RealDType.Aux[Double, DoubleTensor] = Float64DType

  object setFloat32AsDefault {
    implicit def impFloat32DType: RealDType.Aux[Float, FloatTensor] = Float32DType
  }

  object setFloat64AsDefault {
    implicit def impFloat64DType: RealDType.Aux[Double, DoubleTensor] = Float64DType
  }

}
