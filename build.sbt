import com.typesafe.sbt.SbtMultiJvm
import com.typesafe.sbt.SbtMultiJvm.MultiJvmKeys.MultiJvm

name := """kotan"""

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlint",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-unused-import"
)

val akkaVersion = "2.4.16"
val scalatestVersion = "3.0.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
  "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion % "test",
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "org.iq80.leveldb" % "leveldb" % "0.7",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
  "org.scalatest" %% "scalatest" % scalatestVersion % "test"
)

fork in run := true

/*
 * Multi JVM Testing
 */

SbtMultiJvm.multiJvmSettings

compile in MultiJvm <<= ((compile in MultiJvm) triggeredBy (compile in Test))

parallelExecution in Test := false

executeTests in Test := {
  val testResults = (executeTests in Test).value
  val multiNodeResults = (executeTests in MultiJvm).value
  val overall =
    if (testResults.overall.id < multiNodeResults.overall.id)
      multiNodeResults.overall
    else
      testResults.overall
  Tests.Output(overall,
    testResults.events ++ multiNodeResults.events,
    testResults.summaries ++ multiNodeResults.summaries)
}

configs(MultiJvm)

jvmOptions in MultiJvm := Seq("-Xmx128M")

scalatestOptions in MultiJvm := Seq("-u", ((target in Compile).value / "test-reports").getAbsolutePath)

/*
 * Native Packager
 */

enablePlugins(JavaServerAppPackaging)

mainClass in Compile := Some("com.example.ApplicationMain")

