organization  := "au.com.jaylin"

name := "scala-ws"

version := "1.0"

scalaVersion := "2.10.3"

libraryDependencies ++= {
  val apacheCommonsV = "1.8"
  val configV = "1.0.2"
  Seq(
    "com.typesafe"              %   "config"                    % configV, 
    "commons-codec"        		%   "commons-codec" 			% apacheCommonsV
  )
}
