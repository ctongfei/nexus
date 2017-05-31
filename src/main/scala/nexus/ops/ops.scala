package nexus.ops

import nexus._
import shapeless._

/**
 * @author Tongfei Chen
 */
object Add {



}

object Exp {

  def apply[D <: DType, S <: HList](): Op1[D, S, D, S] = new Op1[D, S, D, S] {
    def compute(x: Tensor[D, S]): Tensor[D, S] = {
      ???
    }
    def derivative(dy: Tensor[D, S]): Tensor[D, S] = ???
  }

}