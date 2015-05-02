import sbt.Keys._

name := "tokumei-kokoro"

version := "0.1-alpha"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  cache,
  javaWs,
  filters,
  "org.mindrot" % "jbcrypt" % "0.3m",
  "joda-time" % "joda-time" % "2.3",
  "uk.co.panaxiom" %% "play-jongo" % "0.7.1-jongo1.0",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.1.1",
  "javax.mail" % "mail" % "1.4.7",
  "commons-collections" % "commons-collections" % "3.2.1",
  "commons-lang" % "commons-lang" % "2.6",
  "commons-io" % "commons-io" % "2.4"
)

resolvers ++= Seq(
  "Apache" at "http://repo1.maven.org/maven2/",
  "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
  "Sonatype OSS Snasphots" at "http://oss.sonatype.org/content/repositories/snapshots"
)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding", "UTF-8"
)

parallelExecution in Test := false