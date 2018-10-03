package nexus.tensor.typelevel

import org.scalatest._

/**
 * @author Tongfei Chen
 */
class NestTest extends FunSuite {

  test("Dim-1 array") {

    val a = Array(1, 2, 3)

    val nest0 = Nest[Int, Int]
    val nest1 = Nest[Array[Int], Int]


  }

}
