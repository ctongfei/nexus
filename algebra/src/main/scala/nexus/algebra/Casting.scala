package nexus.algebra

import cats._

/**
 * Evidence that witnesses a type [[S]] can be cast to [[T]].
 * @author Tongfei Chen
 */
trait Casting[S, T] extends (S => T)

trait BidiCasting[S, T] extends Casting[S, T] {
  def invert(t: T): S
}

trait CastingH[S[_ <: $$], T[_ <: $$]] {
  def apply[A <: $$](s: S[A]): T[A]
}

trait BidiCastingH[S[_ <: $$], T[_ <: $$]] extends CastingH[S, T] {
  def invert[A <: $$](t: T[A]): S[A]
}
