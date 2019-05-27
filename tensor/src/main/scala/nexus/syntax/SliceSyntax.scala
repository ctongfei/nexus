package nexus.syntax

import nexus.Slice
import nexus._

trait SliceSyntax {

  implicit class SliceOps(val l: Int) {
    def ~(r: Int): Slice = Slice.Bounded(l, r)
  }

}
