package nexus.algebra.instances

/**
 * @author Tongfei Chen
 */
private[algebra] object all {

  import nexus.algebra.{instances => nxai}

  implicit val Bool = nxai.Bool
  
  implicit val Int8 = nxai.Int8
  implicit val Int16 = nxai.Int16
  implicit val Int32 = nxai.Int32
  implicit val Int64 = nxai.Int64

  implicit val Float32 = nxai.Float32
  implicit val Float64 = nxai.Float64

}
