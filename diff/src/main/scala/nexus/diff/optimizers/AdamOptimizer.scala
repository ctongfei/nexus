package nexus.diff.optimizers

import nexus.diff._
import nexus.syntax._
import scala.math._

/**
 * The Adaptive Moment Estimation ('''Adam''') optimizer.
 *
 * Reference:
 *  - D P Kingma, J L Ba (2015): Adam: A Method for Stochastic Optimization. ICLR. [[https://arxiv.org/pdf/1412.6980.pdf]]
 * @author Tongfei Chen
 * @since 0.1.0
 */
class AdamOptimizer(α: => Double = 0.001, β1: Double = 0.9, β2: Double = 0.999, ε: Double = 1e-6) extends FirstOrderOptimizer {

  /**
   * First-order and second-order momentum stored in the optimizer.
   */
  val history = SymbolicMap[AdamHistory]()

  def updateParam[X](p: Param[X], g: X): Unit = {

    implicit val ev = p.tag.ev
    import ev._

    if (!(history contains p))
      history(p) = AdamHistory(zeroBy(g), zeroBy(g))

    val h = history(p)
    h.m = (h.m :* β1) + (g :* (1 - β1))  // TODO: inplace
    h.v = (h.v :* β2) + ((g |*| g) :* (1 - β2)) // TODO: inplace
    val m̂ = h.m :/ (1 - pow(β1, t))
    val v̂ = h.v :/ (1 - pow(β2, t))
    val Δp = (m̂ |/| (sqrt(v̂) +# ε)) :* α
    p -= Δp
  }

  def parameterMap = history.view.flatMap { e =>
    val p = e.expr.asInstanceOf[Param[e.Data]]  // has to be a Param
    val ev = p.tag.ev
    val m = e.value.m
    val v = e.value.v
    val mName = s"${p.name}.m"
    val vName = s"${p.name}.v"
    List[(String, Param[_])](
      mName -> Param(m, mName)(ev),
      vName -> Param(v, vName)(ev)
    )
  }.toMap

  def loadFromParameterMap(m: Map[String, Param[_]]): Unit = ???

}

private[nexus] case class AdamHistory[X](var m: X, var v: X)
