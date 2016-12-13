name := """kotan"""

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "org.scalatest" %% "scalatest" % scalatestVersion % "test"
)

fork in run := true

enablePlugins(JavaServerAppPackaging)

mainClass in Compile := Some("com.example.ApplicationMain")

lazy val akkaVersion = "2.4.14"
lazy val scalatestVersion = "2.2.4"
