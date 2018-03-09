package nexus.execution

import nexus._


/**
 * Reference:
 * - G Neubig, Y Goldberg, C Dyer (2017): On-the-fly operation batching in dynamic computation graphs. NIPS.
 * [https://arxiv.org/pdf/1705.07860.pdf]
 * @author Tongfei Chen
 */
class AutoBatched(val batchSize: Int) extends ForwardInterpreter {
  def values = ???
  def apply[Y](e: Expr[Y]) = ???
}
