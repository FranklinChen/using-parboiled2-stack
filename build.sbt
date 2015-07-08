name := "using-parboiled2-stack"

organization := "com.franklinchen"

organizationHomepage := Some(url("http://franklinchen.com/"))

homepage := Some(url("http://github.com/FranklinChen/using-parboiled2-stack"))

startYear := Some(2015)

description := "Example of using Parboiled2 stack"

version := "1.0.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.parboiled" %% "parboiled" % "2.1.0"
)
