package nexus.diff

/**
 * @author Tongfei Chen
 */
case class Lambda1[X, Y](x: Input[X], y: Symbolic[Y]) extends Func1[X, Y] {

  def apply(xʹ: Symbolic[X]) = y.substitute(x, xʹ)

}

case class Lambda2[X1, X2, Y](x1: Input[X1], x2: Input[X2], y: Symbolic[Y]) extends Func2[X1, X2, Y] {

  def apply(x1ʹ: Symbolic[X1], x2ʹ: Symbolic[X2]) = y.substitute(x1, x1ʹ).substitute(x2, x2ʹ)

}

case class Lambda3[X1, X2, X3, Y](x1: Input[X1], x2: Input[X2], x3: Input[X3], y: Symbolic[Y]) extends Func3[X1, X2, X3, Y] {

  def apply(x1ʹ: Symbolic[X1], x2ʹ: Symbolic[X2], x3ʹ: Symbolic[X3]) = y.substitute(x1, x1ʹ).substitute(x2, x2ʹ).substitute(x3, x3ʹ)

}
