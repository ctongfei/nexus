package nexus.ops


import cats.Id
import nexus.diff._
import nexus.diff.ops._
import nexus.diff.execution._
import nexus.jvm._
import nexus.diff.modules._
import nexus.diff.syntax._
import nexus._
import nexus.math._
import nexus.syntax._
import nexus.typelevel.InsertAt.Aux
import nexus.typelevel.Remove.Aux
import nexus.typelevel.SymDiff.Aux
import shapeless.Nat

class A extends Dim
class B extends Dim
class C extends Dim

/**
 * @author Tongfei Chen
 */
object RenameTest extends App {

   val A = new A
   val B = new B
   val C = new C

  val a = Input[FloatTensor[(A, B)]]
  val b = a |> RenameAxis(B -> C)

  val x = FloatTensor.fromFlatArray[A](Array(3f, 4f), Array(2))
  val y = FloatTensor.fromFlatArray[A](Array(0f, 0f), Array(2))

  val xi = Input[FloatTensor[A]]
  val yi = Input[FloatTensor[A]]


  val p: FloatTensor[A] = ???

  def add2[F[_], T[_], R, I](x: F[T[I]], y: F[T[I]])(implicit F: Algebra[F], T: IsRealTensorK[T, R]) = Add(x, y)

  val zzzz = add(x, y)
  val zzz = Add(xi, yi)

  val bp = 0

}
