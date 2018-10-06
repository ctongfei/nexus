package nexus

import nexus.tensor._

package object jvm {

  implicit val impFloatTensor: IsRealTensorK[FloatTensor, Float] = FloatTensor
  //implicit val impDoubleTensor: IsRealTensorK[DoubleTensor, Double] = DoubleTensor

}
