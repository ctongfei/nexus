package nexus.tensor.instances

/**
 * @author Tongfei Chen
 */
object all {
  
  implicit val impBool = Bool
  implicit val impboolIdCond = BoolIdCond
  implicit val impboolFunction0Cond = BoolFunction0Cond
  
  implicit val impInt8 = Int8
  implicit val impInt16 = Int16
  implicit val impInt32 = Int32
  implicit val impInt64 = Int64

  implicit val impFloat32 = Float32
  implicit val impFloat64 = Float64

}
