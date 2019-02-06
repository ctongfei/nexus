package nexus

import scala.collection._

/**
 * @author Tongfei Chen
 */
package object workflow {

  implicit class BashJobStringContext(val sc: StringContext) {
    def sh(args: Any*) = {
      val parents: Seq[Job] = args collect {
        case j: Job => j
      }
      val branches = Seq[HAssignment] = args collect {
        case h: HAssignment => ???
      }
      val stringifiedArgs = args collect {
        case j: Job => j.out
        case s => s.toString
      }
      new Job(parents = parents, script = sc.s(stringifiedArgs))
    }
  }

}
