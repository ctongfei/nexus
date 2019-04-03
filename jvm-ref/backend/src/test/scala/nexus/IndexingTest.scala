package nexus

import nexus.syntax._
import nexus.jvm._

class A extends Dim
class B extends Dim
class C extends Dim

object IndexingTest extends App {

  val A = new A
  val B = new B
  val C = new C

  val a: FloatTensor[(A, B, C)] = ???
  val b = a(0 ~ 5, 0, ?)


}
