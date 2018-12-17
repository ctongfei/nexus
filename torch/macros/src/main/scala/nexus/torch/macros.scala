package nexus.torch

import scala.reflect.macros._

/**
 * @author Tongfei Chen
 */
object macros {

  val jniPrefix = "nexus.torch.jni.torchJNI."

  val nexusTorchClassMap = Map(
    "FloatTensor" -> "THFloatTensor",
    "DoubleTensor" -> "THDoubleTensor",
    "LongTensor" -> "THLongTensor",
    "IntTensor" -> "THIntTensor",
    "ShortTensor" -> "THShortTensor",
    "ByteTensor" -> "THCharTensor",
    "BooleanTensor" -> "THByteTensor"
  )

  val nexusTorchMethodMap = Map(
    "add" -> "cadd",
    "sub" -> "csub",
    "mul" -> "cmul",
    "div" -> "cdiv",
    "inv" -> "cinv"
  )

  def getClassName(c: Context) =
    c.enclosingClass.symbol.toString.split(" ").last

  def getMethodName(c: Context) =
    c.enclosingMethod.symbol.toString.split(" ").last

  def getTorchClassName(t: String) = t match {
    case "FloatTensor" => "THFloatTensor"
    case "DoubleTensor" => "THDoubleTensor"
    case "LongTensor" => "THLongTensor"
    case "IntTensor" => "THIntTensor"
    case "ShortTensor" => "THShortTensor"
    case "ByteTensor" => "THCharTensor"
    case "BooleanTensor" => "THByteTensor"
  }

  def getTorchMethodName(t: String) =
    nexusTorchMethodMap.getOrElse(t, t)

  class Info[Ctx <: Context, A](val c: Ctx)(implicit A: Ctx#WeakTypeTag[A]) {
    import c.universe._
    val nexusClassName = getClassName(c)
    val nexusMethodName = getMethodName(c)
    val axesType = weakTypeOf[A]
    val torchClassName = getTorchClassName(nexusClassName)
    val torchMethodName = getTorchMethodName(nexusMethodName)
    val torchNewName = s"${torchClassName}_new"
    val torchFuncName = s"${torchClassName}_${torchMethodName}"

    val nexusType = TypeName(nexusClassName)
    val torchNewFunc = TermName(torchNewName)
    val torchFunc = TermName(torchFuncName)
  }

  def elementwise1[A: c.WeakTypeTag](c: Context)(x: c.Tree): c.Tree = {
    import c.universe._
    val info = new Info[c.type, A](c)
    import info._

    println(s"Macro with native method $torchFuncName generated.")
    val tree = q"""
       val y = new $nexusType[$axesType](nexus.torch.jni.torchJNI.$torchNewFunc())
       nexus.torch.jni.torchJNI.$torchFunc(y.ptr, $x.ptr)
       y
     """

    tree
  }

  def elementwise2[A: c.WeakTypeTag](c: Context)(x: c.Tree, y: c.Tree): c.Tree = {
    import c.universe._
    val info = new Info[c.type, A](c)
    import info._

    println(s"Macro with native method $torchFuncName generated.")
    val tree = q"""
       val z = new $nexusType[$axesType](nexus.torch.jni.torchJNI.$torchNewFunc())
       nexus.torch.jni.torchJNI.$torchFunc(z.ptr, $x.ptr, $y.ptr)
       z
     """

    tree
  }

  def elementwise2addSub[A: c.WeakTypeTag](c: Context)(x: c.Tree, y: c.Tree): c.Tree = {
    import c.universe._
    val info = new Info[c.type, A](c)
    import info._

    println(s"Macro with native method $torchFuncName generated.")
    val tree = q"""
       val z = new $nexusType[$axesType](nexus.torch.jni.torchJNI.$torchNewFunc())
       nexus.torch.jni.torchJNI.$torchFunc(z.ptr, $x.ptr, 1f, $y.ptr)
       z
     """

    tree
  }

}
