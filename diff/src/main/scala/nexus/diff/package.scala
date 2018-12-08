package nexus

import nexus.diff.syntax._
import nexus.diff._

/**
 * '''Nexus''': Typeful & typesafe deep learning.
 * @author Tongfei Chen
 */
package object diff extends ExprRealTensorMixin with SymbolicRealOpsMixin with TupleExprOpsMixin {

  val _0 = shapeless.nat._0
  val _1 = shapeless.nat._1
  val _2 = shapeless.nat._2
  val _3 = shapeless.nat._3
  val _4 = shapeless.nat._4
  val _5 = shapeless.nat._5
  val _6 = shapeless.nat._6
  val _7 = shapeless.nat._7
  val _8 = shapeless.nat._8
  val _9 = shapeless.nat._9

  // ALIASES AND TYPE TAGS FOR BASIC TYPES
  type         Int8    = Byte

  type         Int16   = Short

  type         Int32   = Int

  type         Int64   = Long

  type         Float32 = Float

  type         Float64 = Double

  type Func0[Y]                     = (() => Symbolic[Y])
  type Func1[X1, Y]                 = ((Symbolic[X1]) => Symbolic[Y])
  type Func2[X1, X2, Y]             = ((Symbolic[X1], Symbolic[X2]) => Symbolic[Y])
  type Func3[X1, X2, X3, Y]         = ((Symbolic[X1], Symbolic[X2], Symbolic[X3]) => Symbolic[Y])
  type Func4[X1, X2, X3, X4, Y]     = ((Symbolic[X1], Symbolic[X2], Symbolic[X3], Symbolic[X4]) => Symbolic[Y])
  type Func5[X1, X2, X3, X4, X5, Y] = ((Symbolic[X1], Symbolic[X2], Symbolic[X3], Symbolic[X4], Symbolic[X5]) => Symbolic[Y])


  private[nexus] def typeName(o: Any) = {
    val raw = o.getClass.getTypeName
    val last = raw.split('.').last
    if (last endsWith "$") last.init else last
  }

  private[nexus] implicit class TypeNameStringContextOps(val strCtx: StringContext) extends AnyVal {
    def n(args: Any*) = strCtx.s(args map typeName)
  }

}
