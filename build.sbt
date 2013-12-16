organization  := "au.com.jaylin"

name := "scala-ws"

version := "1.0"

scalaVersion := "2.10.3"

libraryDependencies ++= {
  val apacheCommonsV = "1.8"
  Seq(
    "commons-codec"        		%   "commons-codec" 			% apacheCommonsV
  )
}
