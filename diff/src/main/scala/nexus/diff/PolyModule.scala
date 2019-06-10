package nexus.diff

/**
 * Type polymorphic unary module.
 * @author Tongfei Chen
 */
trait PolyModule1 extends PolyFunc1 {

  trait P[X, Y] extends Module1[X, Y] with Product

  def ground[X, Y](implicit p: P[X, Y]) = p
}

/**
 * Type polymorphic binary module.
 * @see [[PolyModule1]]
 */
trait PolyModule2 extends PolyFunc2 {

  trait P[X1, X2, Y] extends Module2[X1, X2, Y] with Product

  def ground[X1, X2, Y](implicit p: P[X1, X2, Y]) = p

}


/**
 * Type-polymorphic ternary module.
 * @see [[PolyModule1]], [[PolyModule2]]
 */
trait PolyModule3 extends PolyFunc3 {

  trait P[X1, X2, X3, Y] extends Module3[X1, X2, X3, Y] with Product

  def ground[X1, X2, X3, Y](implicit p: P[X1, X2, X3, Y]) = p

}
