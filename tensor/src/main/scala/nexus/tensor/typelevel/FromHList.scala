package nexus.tensor.typelevel

import nexus.tensor._
import shapeless._
import shapeless.ops.hlist._

/**
 * Typelevel function that transforms HLists to tuples / single types.
 * @author Tongfei Chen
 */
trait FromHList[A <: HList] {

  type Out

  def apply(x: A): Out
  def invert(x: Out): A

  def inverse: ToHList.Aux[Out, A]

}

object FromHList {

  def apply[A <: HList](implicit f: FromHList[A]): Aux[A, f.Out] = f
  type Aux[A <: HList, B] = FromHList[A] { type Out = B }

  implicit val hNil: FromHList.Aux[HNil, Unit] =
    new FromHList[HNil] {
      type Out = Unit
      def apply(x: HNil) = ()
      def invert(x: Unit) = HNil
      def inverse = ToHList.unit
    }

  implicit def single[A <: Dim]: FromHList.Aux[A :: HNil, A] =
    new FromHList[A :: HNil] {
      type Out = A
      def apply(x: A :: HNil) = x.head
      def invert(x: A) = x :: HNil
      def inverse = ToHList.single[Out]
    }

  implicit def generic[A <: HList, B](implicit t: Tupler.Aux[A, B], g: Generic.Aux[B, A]): FromHList.Aux[A, B] =
    new FromHList[A] {
      type Out = B
      def apply(x: A) = t(x)
      def invert(x: B) = g.to(x)
      def inverse = ToHList.generic(g, t)
    }
}
