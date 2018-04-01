package nexus.builds

import sbt._

object Libs {
  val dl4jVer = "0.9.1"

  val nd4jNativePlatform = "org.nd4j" % "nd4j-native-platform" % dl4jVer

  val nd4jCuda80Platform = "org.nd4j" % "nd4j-cuda-8.0-platform" % dl4jVer

  val nd4s = "org.nd4j" %% "nd4s" % dl4jVer

}