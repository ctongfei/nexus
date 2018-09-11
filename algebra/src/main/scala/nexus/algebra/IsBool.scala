package nexus.algebra

import cats._
import scala.annotation._

trait IsGenBool[E[_], @specialized(Boolean) B] extends Type[E[B]] {

  def top: E[B]
  def bot: E[B]

  def not(a: E[B]): E[B]
  def and(a: E[B], b: E[B]): E[B]
  def or(a: E[B], b: E[B]): E[B]
  def xor(a: E[B], b: E[B]): E[B]

  def cond[A](c: E[B], t: E[A], f: E[A]): E[A]

}

/**
 * Encapsulates mathematical operations on booleans.
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that type ${B} is a Boolean.")
trait IsBool[@specialized(Boolean) B] extends IsGenBool[Id, B]
