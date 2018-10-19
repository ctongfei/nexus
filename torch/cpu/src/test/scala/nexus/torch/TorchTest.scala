package nexus.torch

import nexus.torch.jni.torchJNI._

/**
 * @author Tongfei Chen
 */
object TorchTest extends App {

  val d = TH_digamma(3.0)
  println(d)

}
