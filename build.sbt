import scala.io._

scalaVersion := "2.12.4"
enablePlugins(ScalaUnidocPlugin)


// credit to http://stackoverflow.com/a/32114551/2990673
lazy val mathFormulaInDoc = taskKey[Unit]("add MathJax script import in scaladoc html to display LaTeX formula")

lazy val commonSettings = Seq(

  organization := "me.tongfei",
  version := "0.1.0-SNAPSHOT",
  isSnapshot := true,
  scalaVersion := "2.12.4",

  libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3",
  libraryDependencies += "org.typelevel" %% "algebra" % "0.7.0",
  libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.0",

  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test,
  libraryDependencies += "me.tongfei" %% "poly-io" % "0.3.2" % Test,

  mathFormulaInDoc := {
    val apiDir = (doc in Compile).value
    val docDir = apiDir    // /"some"/"subfolder"  // in my case, only api/some/solder is parsed
    // will replace this "importTag" by "scriptLine
    // find all html file and apply patch
    if(docDir.isDirectory)
      for (f <- listHtmlFile(docDir)) {
        val content = Source.fromFile(f).getLines().mkString("\n")
        val writer = new java.io.PrintWriter(f)
        writer.write(content.replace(
          "<head>",
          """<head>
             |  <script type="text/x-mathjax-config">
             |    MathJax.Hub.Config({
             |      asciimath2jax: { delimiters: [['「', '」']] }
             |    });
             |  </script>
             |  <script type="text/javascript" async
             |    src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.1/MathJax.js?config=TeX-MML-AM_CHTML">
             |  </script>""".stripMargin))
        writer.close()
      }
  },

  // attach this task to doc task
  mathFormulaInDoc <<= mathFormulaInDoc triggeredBy (doc in Compile)
)


lazy val algebra = (project in file("algebra"))
  .settings(commonSettings: _*)
  .settings(
    name := "nexus-algebra"
  )

lazy val core = (project in file("core"))
  .settings(commonSettings: _*)
  .dependsOn(algebra)
  .settings(
    name := "nexus-core"
  )


lazy val torch = (project in file("torch"))
  .settings(commonSettings: _*)
  .dependsOn(core)
  .settings(
    name := "nexus-torch",
    libraryDependencies += "me.tongfei" % "jtorch-cpu" % "0.3.0-SNAPSHOT"
  )

/*
lazy val cuda = (project in file("cuda"))
  .settings(commonSettings: _*)
  .dependsOn(core)
  .settings(
    name := "nexus-cuda",
    libraryDependencies += "org.bytedeco.javacpp-presets" % "cuda-platform" % "9.0-7.0-1.3"
)

lazy val vision = (project in file("vision"))
  .settings(commonSettings: _*)
  .settings(
    name := "nexus-vision"
  )
*/

// function that find html files recursively
def listHtmlFile(dir: java.io.File): List[java.io.File] = {
  dir.listFiles.toList.flatMap { f =>
    if(f.getName.endsWith(".html")) List(f)
    else if(f.isDirectory)          listHtmlFile(f)
    else                            List[File]()
  }
}
