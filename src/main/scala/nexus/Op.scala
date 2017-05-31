package nexus

import shapeless.HList

/**
 * @author Tongfei Chen
 */
trait Op1[ID <: DType, IS <: HList, OD <: DType, OS <: HList] extends (Expr[ID, IS] => Expr[OD, OS]) {
  def compute(x: Tensor[ID, IS]): Tensor[OD, OS]
  def derivative(dy: Tensor[OD, OS]): Tensor[ID, IS]

  def apply(x: Expr[ID, IS]): Expr[OD, OS] = Output1(this, x)
}

trait Op2[ID1 <: DType, IS1 <: HList, ID2 <: DType, IS2 <: HList, OD <: DType, OS <: HList] extends ((Expr[ID1, IS1], Expr[ID2, IS2]) => Expr[OD, OS]) {
  def compute(x1: Tensor[ID1, IS1], x2: Tensor[ID2, IS2]): Tensor[OD, OS]
  def derivative1(dy: Tensor[OD, OS]): Tensor[ID1, IS1]
  def derivative2(dy: Tensor[OD, OS]): Tensor[ID2, IS2]

  def apply(x1: Expr[ID1, IS1], x2: Expr[ID2, IS2]): Expr[OD, OS] = Output2(this, x1, x2)
}

