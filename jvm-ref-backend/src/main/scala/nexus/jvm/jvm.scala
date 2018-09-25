package nexus

import nexus.algebra._
import nexus.algebra.instances.all._

package object jvm {


  implicit val impFloatTensor: IsRealTensorK[FloatTensor, Float] = FloatTensor

}
