package nexus

import cats._

/**
 * @author Tongfei Chen
 */
package object instances {

  implicit val impBoolIsBool: IsBool[Boolean] = BoolIsBool
  implicit val impBoolIdCond: Cond[Boolean, Id] = BoolIdCond

  implicit val impByteIsInt: IsInt[Byte] = ByteIsInt
  implicit val impShortIsInt: IsInt[Short] = ShortIsInt
  implicit val impIntIsInt: IsInt[Int] = IntIsInt
  implicit val impLongIsInt: IsInt[Long] = LongIsInt

  implicit val impFloatIsReal: IsReal[Float] = FloatIsReal
  implicit val impDoubleIsReal: IsReal[Double] = DoubleIsReal

}
