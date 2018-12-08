package nexus.tensor.util

import scala.language.experimental.macros
import scala.reflect.macros.blackbox._

/**
 * @author Tongfei Chen
 */
object OpsInlining {

  def opName(c: Context)(op: c.TermName): c.TermName = {
    import c.universe._
    op match {
      case TermName("unary_$minus")   => TermName("neg")
      case TermName("$plus")          => TermName("add")
      case TermName("$minus")         => TermName("sub")
      case TermName("$times")         => TermName("mul")
      case TermName("$div")           => TermName("div")
      case TermName("$colon$times")   => TermName("scale")
      case TermName("$times$colon")   => TermName("scale")
      case TermName("$bar$times$bar") => TermName("mul")
      case TermName("$bar$div$bar")   => TermName("div")
      case _                          => op
    }
  }

  def op1(c: Context): c.Tree = {
    import c.universe._
    c.macroApplication match {
      case q"$impConv($lhs)($ev).$method" =>
        q"$ev.${opName(c)(method)}($lhs)"
    }
  }

  def op2(c: Context)(y: c.Tree): c.Tree = {
    import c.universe._
    c.macroApplication match {
      case q"$impConv($lhs)($ev).$method($rhs)" =>
        q"$ev.${opName(c)(method)}($lhs, $rhs)"
      case q"$impConv($lhs)($ev).$method[..$t]($rhs)" =>
        q"$ev.${opName(c)(method)}($lhs, $rhs)"
    }
  }

  def op2Float(c: Context)(y: c.Tree): c.Tree = {
    import c.universe._
    c.macroApplication match {
      case q"$impConv($lhs)($ev).$method($rhs)" =>
        q"$ev.${opName(c)(method)}($lhs, $ev.fromFloat($rhs))"
      case q"$impConv($lhs)($ev).$method[..$t]($rhs)" =>
        q"$ev.${opName(c)(method)}($lhs, $ev.fromFloat($rhs))"
    }
  }

  def op2Double(c: Context)(y: c.Tree): c.Tree = {
    import c.universe._
    c.macroApplication match {
      case q"$impConv($lhs)($ev).$method($rhs)" =>
        q"$ev.${opName(c)(method)}($lhs, $ev.fromDouble($rhs))"
      case q"$impConv($lhs)($ev).$method[..$t]($rhs)" =>
        q"$ev.${opName(c)(method)}($lhs, $ev.fromDouble($rhs))"
    }
  }

  def op2TRFloat(c: Context)(y: c.Tree): c.Tree = {
    import c.universe._
    c.macroApplication match {
      case q"$impConv($lhs)($ev).$method($rhs)" =>
        q"$ev.${opName(c)(method)}($lhs, $ev.R.fromFloat($rhs))"
      case q"$impConv($lhs)($ev).$method[..$t]($rhs)" =>
        q"$ev.${opName(c)(method)}($lhs, $ev.R.fromFloat($rhs))"
    }
  }

  def op2TRDouble(c: Context)(y: c.Tree): c.Tree = {
    import c.universe._
    c.macroApplication match {
      case q"$impConv($lhs)($ev).$method($rhs)" =>
        q"$ev.${opName(c)(method)}($lhs, $ev.R.fromDouble($rhs))"
      case q"$impConv($lhs)($ev).$method[..$t]($rhs)" =>
        q"$ev.${opName(c)(method)}($lhs, $ev.R.fromDouble($rhs))"
    }
  }

}
