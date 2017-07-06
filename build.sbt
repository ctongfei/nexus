import scala.io._

name := "nexus"
organization := "me.tongfei"
version := "0.0.1-SNAPSHOT"
isSnapshot := true
scalaVersion := "2.12.2"

libraryDependencies += "com.chuusai" %% "shapeless"   % "2.3.2"

// credit to http://stackoverflow.com/a/32114551/2990673
lazy val mathFormulaInDoc = taskKey[Unit]("add MathJax script import in scaladoc html to display LaTeX formula")

mathFormulaInDoc := {
  val apiDir = (doc in Compile).value
  val docDir = apiDir    // /"some"/"subfolder"  // in my case, only api/some/solder is parsed
  // will replace this "importTag" by "scriptLine
  // find all html file and apply patch
  if(docDir.isDirectory)
    listHtmlFile(docDir).foreach { f =>
      val content = Source.fromFile(f).getLines().mkString("\n")
      val writer = new java.io.PrintWriter(f)
      writer.write(content.replace("<head>", """<head><script type="text/javascript" src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"> </script>"""))
      writer.close()
    }
}

// attach this task to doc task
mathFormulaInDoc <<= mathFormulaInDoc triggeredBy (doc in Compile)

// function that find html files recursively
def listHtmlFile(dir: java.io.File): List[java.io.File] = {
  dir.listFiles.toList.flatMap { f =>
    if(f.getName.endsWith(".html")) List(f)
    else if(f.isDirectory)          listHtmlFile(f)
    else                            List[File]()
  }
}
