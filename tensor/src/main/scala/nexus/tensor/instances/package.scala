package nexus.tensor

/**
 * @author Tongfei Chen
 */
package object instances {

  implicit val impBoolIsBool = Bool
  implicit val impBoolIdCond = BoolIdCond
  implicit val impBoolFunction0Cond = BoolFunction0Cond

  implicit val impByteIsInt = ByteIsInt
  implicit val impShortIsInt = ShortIsInt
  implicit val impIntIsInt = IntIsInt
  implicit val impLongIsInt = LongIsInt

  implicit val impFloatIsReal = FloatIsReal
  implicit val impDoubleIsReal = DoubleIsReal

}
