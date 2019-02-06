package nexus.diff

/**
 * Type polymorphic unary module.
 * @author Tongfei Chen
 */
trait PolyModule1 extends PolyFunc1 {

  trait F[X, Y] extends Module1[X, Y]

  def ground[X, Y](implicit p: F[X, Y]) = p
}

/**
 * Type polymorphic binary module.
 * @see [[PolyModule1]]
 */
trait PolyModule2 extends PolyFunc2 {

  trait F[X1, X2, Y] extends Module2[X1, X2, Y]

  def ground[X1, X2, Y](implicit p: F[X1, X2, Y]) = p

}


/**
 * Type-polymorphic ternary module.
 * @see [[PolyModule1]], [[PolyModule2]]
 */
trait PolyModule3 extends PolyFunc3 {

  trait F[X1, X2, X3, Y] extends Module3[X1, X2, X3, Y]

  def ground[X1, X2, X3, Y](implicit p: F[X1, X2, X3, Y]) = p

}
