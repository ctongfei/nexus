package nexus

import shapeless.HList

import scala.annotation._

/**
 * Runtime environment of a tensor of type T and data type D.
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot find a device to run ${T} with element type ${D}.")
trait Env[T[_, _ <: HList], D] {

  def neg[A <: HList](x: T[D, A]): T[D, A]
  def add[A <: HList](x: T[D, A], y: T[D, A]): T[D, A]
  def sub[A <: HList](x: T[D, A], y: T[D, A]): T[D, A]
  def mul[A <: HList](x: T[D, A], y: T[D, A]): T[D, A]
  def div[A <: HList](x: T[D, A], y: T[D, A]): T[D, A]

}
