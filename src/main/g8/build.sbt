// give the user a nice default project!
ThisBuild / organization := "$organization$"
ThisBuild / scalaVersion := "2.12.8"

// this starter only works for 1.14.x
lazy val flinkVersion = "1.14.4"

lazy val flinkDeps = Seq("flink-streaming-scala").map(
  artifact => "org.apache.flink" %% artifact % flinkVersion % Provided
)

lazy val testDeps = Seq(
  "com.disneystreaming"  %% "weaver-cats"           % "0.6.12",
  "org.apache.flink"     %% "flink-test-utils"      % flinkVersion,
  "org.apache.flink"      % "flink-connector-files" % flinkVersion,
  "com.github.tototoshi" %% "scala-csv"             % "1.3.10"
).map(_ % Test)

lazy val root = (project in file(".")).
  settings(
    name := "$name$",
    // forking is necessary for test to work, or else serviceLoader wont be able to locate Flink RPC correctly
    Test / fork := true
  ).settings(
    libraryDependencies ++= flinkDeps ++ testDeps,
    testFrameworks += new TestFramework("weaver.framework.CatsEffect")
)
