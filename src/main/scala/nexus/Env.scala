package nexus

import shapeless.{HList, HNil}

import scala.annotation._

/**
 * Runtime environment of a tensor of type T and data type D.
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot find a device to run ${UT} with element type ${D}.")
trait Env[UT[_], D] {

  def neg(x: UT[D]): UT[D]
  def add(x: UT[D], y: UT[D]): UT[D]
  def sub(x: UT[D], y: UT[D]): UT[D]
  def eMul(x: UT[D], y: UT[D]): UT[D]
  def eDiv(x: UT[D], y: UT[D]): UT[D]


}
