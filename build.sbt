name := "scala string course day 5"

version := "0.0.1-SNAPSHOT"

lazy val javaVersion = "1.8"

scalaVersion := "2.12.2"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8", // yes, this is 2 args
  "-feature",
  "-language:implicitConversions",
  "-unchecked",
  "-Xlint",
  "-target:jvm-".concat(javaVersion)
)

javacOptions ++= Seq(
  "-Xms2g",
  "-Xmx4g",
  "-Xss2g",
  "-source", javaVersion,
  "-target", javaVersion
)

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6", //withSources() withJavadoc(),
  "com.typesafe.akka" %% "akka-actor" % "2.5.0" withSources() withJavadoc(),
  "junit" % "junit" % "4.12" % Test, //withSources() withJavadoc(),
  "com.novocode" % "junit-interface" % "0.11" % Test, //withSources() withJavadoc(),
  "org.scalatest" %% "scalatest" % "3.0.1" % Test //withSources() withJavadoc()
)
