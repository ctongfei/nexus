package nexus.functions

import algebra.ring.{Field, Ring}


trait ScalarFunctions {
  // TODO: Make everything in here macros

  /** Adds two scalars. */
  def add[R](x: R, y: R)(implicit R: Ring[R]): R = R.plus(x, y)

  /** Negates a scalar. */
  def neg[R](x: R)(implicit R: Ring[R]): R = R.negate(x)

  /** Subtracts a scalar from another. */
  def sub[R](x: R, y: R)(implicit R: Ring[R]): R = R.minus(x, y)

  /** Multiplies two scalars. */
  def mul[R](x: R, y: R)(implicit R: Ring[R]): R = R.times(x, y)

  /** Reciprocal of a scalar. */
  def inv[R](x: R)(implicit R: Field[R]): R = R.reciprocal(x)

  /** Divides a scalar by  another. */
  def div[R](x: R, y: R)(implicit R: Field[R]): R = R.div(x, y)

}
