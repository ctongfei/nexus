package nexus.op.properties

import nexus._
import nexus.algebra._
import nexus.op._
import nexus.prob._
import org.scalatest._

/**
 * @author Tongfei Chen
 */
class OpSSTests[R](gen: Gen[R])(implicit R: IsReal[R]) extends FunSuite {

  val opsOnReal: Seq[Op1[R, R]] = Seq(
    Id.idF[R],
    Neg.negF[R],
    Inv.invF[R],
    Sin.sinF[R],
    Cos.cosF[R],
    Exp.expF[R],
    Abs.absF[R],
    Sqr.sqrF[R]
  )

  for (op <- opsOnReal) {
    test(s"${op.name}'s automatic derivative is close to its numerical approximation on $R") {
      val prop = new OpSSProps(op, gen)
      assert(prop.passedCheck())
    }
  }

  val opsOnPositiveReal: Seq[Op1[R, R]] = Seq(
    Log.logF[R],
    Sqrt.sqrtF[R]
  )

  for (op <- opsOnPositiveReal) {
    test(s"${op.name}'s automatic derivative is close to its numerical approximation on $R") {
      val prop = new OpSSProps(op, gen.filter(x => R.toFloat(x) > 0))
      assert(prop.passedCheck())
    }
  }


}
