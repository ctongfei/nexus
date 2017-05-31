package nexus

import shapeless._
import shapeless.ops.hlist._
import shapeless.ops.nat._

/**
 * @author Tongfei Chen
 */
trait Expr[D <: DType, S <: HList] {

  type Rank = Length[S]#Out
  def rank(implicit e: ToInt[Rank]) = e()

}

case class Const[D <: DType, S <: HList](value: Tensor[D, S]) extends Expr[D, S]

case class Parameter[D <: DType, S <: HList](value: Tensor[D, S]) extends Expr[D, S]

case class Output1[ID <: DType, IS <: HList, OD <: DType, OS <: HList](op: Op1[ID, IS, OD, OS], x: Expr[ID, IS]) extends Expr[OD, OS]

case class Output2[ID1 <: DType, IS1 <: HList, ID2 <: DType, IS2 <: HList, OD <: DType, OS <: HList](op: Op2[ID1, IS1, ID2, IS2, OD, OS], x1: Expr[ID1, IS1], x2: Expr[ID2, IS2]) extends Expr[OD, OS]
