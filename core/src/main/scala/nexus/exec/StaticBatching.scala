package nexus.exec

import nexus._

/**
 * @author Tongfei Chen
 */
class Batch

object StaticBatching {

  val Batch = new Batch

  def batchTensor[T[_ <: $$], A <: $$](e: Expr[T[A]], n: Int): Expr[T[Batch::A]] = e match {
    case e: Input[T[A]] => Input[T[Batch::A]](name = s"${e.name}_batched")
    case e: Const[T[A]] => ??? // Broadcast(Batch)(e)
    case e: Param[T[A]] => ??? // Broadcast(Batch)(e)
    case e @ DApply1(f, x) => ???

  }


}
