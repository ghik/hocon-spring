name := "hocon-spring"
organization := "hocon-spring"
version := "0.9"

scalaVersion := "2.11.7"
crossPaths := false
scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions",
  "-language:existentials",
  "-language:dynamics",
  "-language:experimental.macros",
  "-Xfuture",
  "-Xfatal-warnings",
  "-Xlint:_,-missing-interpolator,-adapted-args"
)

val springVersion = "4.0.2.RELEASE"
val typesafeConfigVersion = "1.3.0"
val scalatestVersion = "2.2.5"
val silencerVersion = "0.3"

libraryDependencies ++= Seq(
  "com.github.ghik" % "silencer-lib" % silencerVersion,
  compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion),
  "org.springframework" % "spring-context" % springVersion,
  "com.typesafe" % "config" % typesafeConfigVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % Test
)
