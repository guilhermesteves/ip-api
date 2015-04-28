import sbt.Keys._

name := """tokumei-kokoro"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

lazy val root = (project in file(".")).enablePlugins(play.PlayJava)

libraryDependencies ++= Seq(
  jdbc,
  javaEbean,
  javaWs,
  cache,
  "org.mindrot" % "jbcrypt" % "0.3m",
  "joda-time" % "joda-time" % "2.3",
  "uk.co.panaxiom" %% "play-jongo" % "0.7.1-jongo1.0",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.1.1",
  "javax.mail" % "mail" % "1.4.7",
  "commons-collections" % "commons-collections" % "3.2.1",
  "commons-lang" % "commons-lang" % "2.6",
  "commons-io" % "commons-io" % "2.4",
  filters
)

resolvers ++= Seq(
    "Apache" at "http://repo1.maven.org/maven2/",
    "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
    "Sonatype OSS Snasphots" at "http://oss.sonatype.org/content/repositories/snapshots"
)
