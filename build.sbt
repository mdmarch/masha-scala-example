ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.3"

val zioVersion            = "2.1.16"
val zioHttpVersion        = "3.0.1"
val zioJsonVersion        = "0.7.7"
val zioPreludeVersion     = "1.0.0-RC39"
val zioConfigVersion      = "4.0.3"
val zioLoggingVersion     = "2.5.0"

lazy val root = (project in file("."))
  .settings(
    name := "untitled"
  )

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-http" % zioHttpVersion,
  "dev.zio" %% "zio-config" % zioConfigVersion,
  "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
  "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
  "dev.zio" %% "zio-json" % zioJsonVersion,
  "dev.zio" %% "zio-prelude" % zioPreludeVersion,
)