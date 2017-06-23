package nexus

import shapeless._
import scala.annotation._

/**
 * Runtime environment of a tensor of type T and data type D.
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot find a device to run ${UT} with element type ${D}.")
trait Env[UT[_], D] {

  def zero: D
  def one: D
  def log(x: UT[D]): UT[D]

  def getScalar(x: UT[D]): D
  def scalar(x: D): UT[D]

  def sigmoid(x: UT[D]): UT[D]

  def addInplace(x: UT[D], d: UT[D]): Unit

  def addS(x: UT[D], u: D): UT[D]

  def neg(x: UT[D]): UT[D]
  def add(x: UT[D], y: UT[D]): UT[D]
  def sub(x: UT[D], y: UT[D]): UT[D]
  def mul(x: UT[D], y: UT[D]): UT[D]
  def div(x: UT[D], y: UT[D]): UT[D]

  def inv(x: UT[D]): UT[D]

  def transpose(x: UT[D]): UT[D]

  def mvMul(x: UT[D], y: UT[D]): UT[D]
  def vvMul(x: UT[D], y: UT[D]): UT[D]


}
