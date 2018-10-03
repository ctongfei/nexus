package nexus.tensor.typelevel

import org.scalatest._
import shapeless._

/**
 * @author Tongfei Chen
 */
class IndexerTest extends FunSuite {

  object A
  object B
  object C
  type X = A.type :: B.type :: C.type :: HNil

  test("Indexer") {

  }

}
