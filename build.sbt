name := "scalpaca"

version := "0.1"

//scalaVersion := "2.12.4"
scalaVersion := "2.12.8"

scalacOptions ++= Seq("-Ypartial-unification", "-unchecked", "-deprecation")

updateOptions := updateOptions.value.withCachedResolution(true)


libraryDependencies += "org.typelevel" % "cats-core_2.12" % "1.5.0"
libraryDependencies += "org.typelevel" % "cats-effect_2.12" % "1.1.0"
libraryDependencies += "io.circe" % "circe-core_2.12" % "0.11.1"
libraryDependencies += "io.circe" % "circe-generic_2.12" % "0.11.1"
libraryDependencies += "io.circe" % "circe-parser_2.12" % "0.11.1"
libraryDependencies += "io.circe" % "circe-java8_2.12" % "0.11.1"
libraryDependencies += "org.http4s" % "http4s-circe_2.12" % "0.20.0-M4"
libraryDependencies += "org.http4s" % "http4s-dsl_2.12" % "0.20.0-M4"
libraryDependencies += "org.http4s" % "http4s-blaze-client_2.12" % "0.20.0-M4"

//libraryDependencies += "org.typelevel" % "cats-core" % "1.5.0"
//libraryDependencies += "org.typelevel" % "cats-effect_2.12" % "1.1.0"