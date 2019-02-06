package nexus.typelevel

import shapeless._

/**
 * @author Tongfei Chen
 */
trait Union[U, V] extends DepFn2[U, V] {
  type Out
  def apply(t: U, u: V): Out
}

object Union {

  type Aux[U, V, W] = Union[U, V] { type Out = W }
  def apply[U, V, W](implicit u: Union[U, V]): Union.Aux[U, V, u.Out] = u

  implicit def hList[U <: HList, V <: HList, W <: HList]
  (implicit s: ops.hlist.Union.Aux[U, V, W]): Union.Aux[U, V, W] =
    new Union[U, V] {
      type Out = W
      def apply(t: U, u: V) = s(t, u)
    }

  implicit def tuple[U, Ul <: HList, V, Vl <: HList, Wl <: HList, W]
  (implicit ul: ToHList.Aux[U, Ul], vl: ToHList.Aux[V, Vl], s: Union.Aux[Ul, Vl, Wl], wl: FromHList.Aux[Wl, W]): Union.Aux[U, V, W] =
    new Union[U, V] {
      type Out = W
      def apply(u: U, v: V) = wl(s(ul(u), vl(v)))
    }

}
