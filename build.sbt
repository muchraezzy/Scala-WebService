//config = config("http") 


name := "SlickTEST"

version := "1.0"

scalaVersion := "2.12.15"


libraryDependencies ++= {
  Seq(
    "net.sourceforge.jtds"      %     "jtds" % "1.3.1",
    "com.typesafe.slick"        %%    "slick"               %   "3.3.0",
    //"com.typesafe.slick"        %%    "slick-extensions"    %   "3.0.0",
     "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3",
    "org.scala-lang.modules"    %%     "scala-async"    %   "1.0.1",
    "com.microsoft.sqlserver" % "mssql-jdbc" % "6.2.1.jre8",
       // "com.typesafe.akka" %% "akka-actor-typed" % "2.5.21",
        "com.typesafe.akka" %% "akka-actor" % "2.6.16",
       // "com.typesafe.akka" %% "akka-stream-typed" % "2.5.21",
         "com.typesafe.akka" %% "akka-stream" % "2.6.16",
      //  "com.typesafe.akka" %% "akka-slf4j" % "2.5.21",
        "com.typesafe.akka" %% "akka-protobuf" % "2.6.16",
        "com.typesafe.akka" %% "akka-http" % "10.2.7",
        "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.7",
       // "io.spray" %% "spray-can" %  "1.3.4",
        //"io.spray" %% "spray-routing" %  "1.3.4",
        "ch.megard" %% "akka-http-cors" % "1.1.2",
         "de.heikoseeberger" %% "akka-http-circe" % "1.38.2",
        "io.circe"        %% "circe-generic"       % "0.14.0",
        "org.http4s"      %% "http4s-blaze-server" % "1.0.0-M23",
        "org.http4s"      %% "http4s-circe"        % "1.0.0-M23",
        "org.http4s"      %% "http4s-dsl"          % "1.0.0-M23",
         "org.scala-lang" % "scala-reflect" % "2.12.15" ,
          "org.typelevel" %% "cats-core" % "2.6.1",
              "org.json4s"        %% "json4s-native"   % "4.0.3",
    "org.json4s"        %% "json4s-ext"      % "4.0.3",
    "de.heikoseeberger" %% "akka-http-json4s" % "1.38.2"
  )
}

resolvers ++= Seq ("Typesafe Releases" at "https://repo.typesafe.com/typesafe/maven-releases/",
                  ("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/").withAllowInsecureProtocol(true),
                  ("Spray Repository" at "http://repo.spray.io").withAllowInsecureProtocol(true)
  )

  // for debugging sbt problems
logLevel := Level.Debug

scalacOptions ++= Seq(
   "-deprecation",         // emit warning and location for usages of deprecated APIs
  //  "-explain",             // explain errors in more detail
  //  "-explain-types",       // explain type errors in more detail
   "-feature",             // emit warning and location for usages of features that should be imported explicitly
  //  "-indent",              // allow significant indentation.
  //  "-new-syntax",          // require `then` and `do` in control expressions.
  //  "-print-lines",         // show source code line numbers.
   "-unchecked",           // enable additional warnings where generated code depends on assumptions
  //  "-Ykind-projector",     // allow `*` as wildcard to be compatible with kind projector
   "-Xfatal-warnings",     // fail the compilation if there are any warnings
  //  "-Xmigration"           // warn about constructs whose behavior may have changed since version
   "-encoding", "utf8", // Option and arguments on same line
   "-Xasync",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps"
)

//Global / mainClass:= Some("main")
//Global / onChangedBuildSource := ReloadOnSourceChanges
