package nexus.algebra.typelevel

import shapeless._

/**
 * Witnesses that a type can be used as an axis label.
 * @author Tongfei Chen
 */
trait Label[A] { }

object Label {

  private object singleton extends Label[Nothing]

  def apply[A](implicit A: Label[A]) = A

  implicit def isLabel[A](implicit np: A <:!< Product, nh: A <:!< HList, nu: A =:!= Unit): Label[A]
    = singleton.asInstanceOf[Label[A]]

}
