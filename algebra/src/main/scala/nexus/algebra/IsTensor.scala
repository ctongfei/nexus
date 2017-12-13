package nexus.algebra

import nexus._

/**
 * Typeclass that describes the algebraic structures on tensors with arbitrary element type.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait IsTensor[T[_ <: $$], E] extends TypeH[T] with AxisTyping[T] { self =>

  val H: IsUntypedTensor[H, E]

  def newTensor[A <: $$](axes: A, shape: Array[Int]): T[A]

  def wrapScalar(x: E): T[$] = typeWith(H.wrapScalar(x), $)

  def unwrapScalar(x: T[$]): E = H.unwrapScalar(untype(x))

  def map[A <: $$](x: T[A])(f: E => E): T[A] =
    typeWith(H.map(untype(x))(f), typeOf(x))

  def map2[A <: $$](x1: T[A], x2: T[A])(f: (E, E) => E): T[A] =
    typeWith(H.map2(untype(x1), untype(x2))(f), typeOf(x1))

  def map3[A <: $$](x1: T[A], x2: T[A], x3: T[A])(f: (E, E, E) => E): T[A] =
    typeWith(H.map3(untype(x1), untype(x2), untype(x3))(f), typeOf(x1))

}
