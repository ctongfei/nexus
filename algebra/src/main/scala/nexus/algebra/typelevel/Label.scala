package nexus.algebra.typelevel

import shapeless._

/**
 * @author Tongfei Chen
 */
trait Label[A]

object Label {

  def apply[A](implicit A: Label[A]) = A

  implicit def isLabel[A](implicit np: A <:!< Product, nh: A <:!< HList, nu: A =:!= Unit): Label[A]
    = new Label[A] {}

}
