name := "tokumei-kokoro"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

packageArchetype.java_server

libraryDependencies ++= Seq(
  cache,
  javaWs,
  filters,
  "joda-time" % "joda-time" % "2.3",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "uk.co.panaxiom" %% "play-jongo" % "0.7.1-jongo1.0",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.1.1",
  "javax.mail" % "mail" % "1.4.7",
  "commons-collections" % "commons-collections" % "3.2.1",
  "commons-lang" % "commons-lang" % "2.6",
  "commons-io" % "commons-io" % "2.4",
  "com.google.code.maven-play-plugin.net.tanesha.recaptcha4j" % "recaptcha4j" % "0.0.8"
)

resolvers ++= Seq(
  "Apache" at "http://repo1.maven.org/maven2/",
  "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
  "JBoss Repo" at "https://repository.jboss.org/nexus/content/groups/public",
  "Sonatype OSS Snasphots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "antelink" at "http://maven.antelink.com/content/repositories/central/"
)