package nexus.testbase.ops

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.ops._
import nexus.prob._
import org.scalatest._

/**
 * Tests R^n^ -> R functions.
 * @author Tongfei Chen
 */
class OpVSTests[T[_], R](gen: Stochastic[R])(implicit T: IsRealTensorK[T, R]) extends FunSuite {


  class Axis
  val len = 10

  class Prop(op: Op1[T[Axis], R], gen: Stochastic[T[Axis]]) extends ApproxProp[T[Axis], R](op, gen) {
    implicit val R = T.R

    def autoGrad(x: T[Axis]) = {
      val y = op.forward(x)
      op.backward(R.one, y, x)
    }

    def numGrad(x: T[Axis]) =
      T.tabulate[Axis](x.shape(0)) { i =>
        val z: R = x(i)
        val δ = z * relativeDiff
        val δx = T.tabulate[Axis](x.shape(0)) { j => if (j == i) δ else R.zero }
        (op.forward(x + δx) - op.forward(x - δx)) / (δ * 2d)
      }

    def error(ag: T[Axis], ng: T[Axis]): R = L2Norm(ag - ng) / L2Norm(ag)
  }

  val ops = Seq(
    L1Norm.l1NormF[T, R, Axis],
    L2Norm.l2NormF[T, R, Axis]
  )

  for (op <- ops) {
    test(s"${op.name}'s automatic derivative is close to its numerical approximation on $T") {
      val prop = new Prop(op, Stochastic.apply(T.tabulate(len)(_ => gen.sample)))
      assert(prop.passedCheck())
    }
  }

}
