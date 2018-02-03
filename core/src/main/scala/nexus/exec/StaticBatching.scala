package nexus.exec

import nexus._
import nexus.algebra._
import nexus.op._
import nexus.op.seq._

/**
 * @author Tongfei Chen
 */
class Batch

object StaticBatching {

  val Batch = new Batch

  private def batch0[X](x: Expr[X], batchSize: Int): Expr[Seq[X]] = x match {

    case Input(name) => Input[Seq[X]](s"$name [batched]")

    case _: Const[X] | _: Param[X] => x.tag match {
      case tag: Grad[X] => Repeat.repeatF(tag)(x, Const(batchSize))
      case _ => Repeat.repeatNF(x, Const(batchSize))
    }



  }


}
