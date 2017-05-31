package nexus.autodiff

import nexus._
import shapeless.HList

import scala.collection._

/**
 * @author Tongfei Chen
 */
class Values {

  private val map = mutable.HashMap[Expr[_, _], Tensor[_, _]]()

  def apply[D <: DType, S <: HList](e: Expr[D, S]): Tensor[D, S] =
    map(e).asInstanceOf[Tensor[D, S]]

}
