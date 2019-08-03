name := "scalpaca"

version := "0.1"

scalaVersion := "2.12.8"

scalacOptions ++= Seq("-Ypartial-unification", "-unchecked", "-deprecation")

updateOptions := updateOptions.value.withCachedResolution(true)

val http4sVersion = "0.20.7"
val circeVersion  = "0.11.1"

libraryDependencies ++= Seq(
  "org.typelevel"  % "cats-core_2.12"           % "1.6.0"       withSources() withJavadoc(),
  "org.typelevel"  % "cats-effect_2.12"         % "1.3.1"       withSources() withJavadoc(),
  "io.circe"       % "circe-core_2.12"          % circeVersion  withSources() withJavadoc(),
  "io.circe"       % "circe-generic_2.12"       % circeVersion  withSources() withJavadoc(),
  "io.circe"       % "circe-parser_2.12"        % circeVersion  withSources() withJavadoc(),
  "io.circe"       % "circe-java8_2.12"         % circeVersion  withSources() withJavadoc(),
  "org.http4s"     % "http4s-circe_2.12"        % http4sVersion withSources() withJavadoc(),
  "org.http4s"     % "http4s-dsl_2.12"          % http4sVersion withSources() withJavadoc(),
  "org.http4s"     % "http4s-blaze-client_2.12" % http4sVersion withSources() withJavadoc(),
  "ch.qos.logback" % "logback-classic"          % "1.2.3"       withSources() withJavadoc(),
)