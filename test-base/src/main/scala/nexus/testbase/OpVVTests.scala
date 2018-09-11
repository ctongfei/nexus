package nexus.testbase

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.ops._
import nexus.prob._
import org.scalatest._

/**
 * Tests R^n^ -> R^n^ functions.
 * @author Tongfei Chen
 */
class OpVVTests[T[_], R](gen: Stochastic[R])(implicit T: IsRealTensorK[T, R]) extends FunSuite {

  class Axis
  val len = 10

  class Prop(op: Op1[T[Axis], T[Axis]], gen: Stochastic[T[Axis]]) extends ApproxProp[T[Axis], R](op, gen) {
    implicit val R = T.R
    val dy = gen.sample

    def autoGrad(x: T[Axis]) = {
      val y = op.forward(x)
      op.backward(dy, y, x)
    }

    def numGrad(x: T[Axis]) = {
      T.tabulate(x.shape(0)) { i =>
        val δ = x(i) * relativeDiff
        val δx = T.tabulate[Axis](x.shape(0)) { j => if (j == i) δ else R.zero }
        (dy dot (op.forward(x + δx) - op.forward(x - δx))) / (δ * 2d)
      }
    }

    def error(ag: T[Axis], ng: T[Axis]) = L2Norm(ag - ng) / L2Norm(ag)
  }

  val ops = Seq(
    Id.idF[T[Axis]],
    Neg.negF[T[Axis]],
    Inv.Elementwise.invElementwiseF[T, R, Axis],
    Exp.Elementwise.expElementwiseF[T, R, Axis],
    ReLU.reLUF[T, R, Axis],
    Softmax.softmaxF[T, R, Axis]
  )

  for (op <- ops) {
    test(s"${op.name}'s automatic derivative is close to its numerical approximation on $T") {
      val prop = new Prop(op, Stochastic(T.tabulate(len)(_ => gen.sample)))
      assert(prop.passedCheck())
    }
  }

  //TODO: Log on R+

}
