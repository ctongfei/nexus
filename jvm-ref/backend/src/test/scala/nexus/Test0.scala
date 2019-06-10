package nexus

import nexus.diff._
import nexus.diff.execution._
import nexus.jvm._
import nexus.jvm.setFloat32AsDefault._
import nexus.diff.ops._
import nexus.instances._
import nexus.diff.syntax._
import nexus.diff.optimizers._

/**
 * Vanilla test to see if p converges to 0.
 * @author Tongfei Chen
 */
class a extends Dim
class b extends Dim
class c extends Dim

object Test0 extends App {

  val a = new a
  val b = new b
  val c = new c

  val z = FloatTensor.newTensor[(a, b, c)](Array(3, 2, 1))


  val p: Symbolic[Float] = Param(3.0f, name = "p")

  val q: Symbolic[FloatTensor[Unit]] = Param(FloatTensor.fromFlatArray((), Array(), Array(1f)), name = "q")

  val u: Symbolic[FloatTensor[(a, b)]] = ???
  val v: Symbolic[FloatTensor[(b, c)]] = ???

  val w = Contract(u, v)

  val t = p |> Exp
  val zzz = t + t
  val tt = q |> Exp
  val loss = p |> Sqr

  val sgd = new GradientDescentOptimizer(0.1)

  for (i <- 0 until 100) {
    implicit val forward = SymbolicForward.given()
    println(s"$i: p = ${p.value}; \t Value = ${loss.value}")

    val grad = ∂(loss) / ∂(p)

    val gradients = loss.gradients
    sgd.update(gradients)
  }

}
