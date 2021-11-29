// //akka server imports
// import akka.actor._  
// import akka.actor.Props;  
// import akka.actor.ActorSystem
// import akka.actor.typed.scaladsl.Behaviors

// //akka http
// import akka.http.scaladsl.Http 
// import akka.http.scaladsl.model._
// import akka.http.scaladsl.server.Route
// import akka.http.scaladsl.server.Directives._


// //spray JSON marshalling
// //import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport.*
// import spray.json._
// import spray.http.HttpHeaders
// import spray.routing._
// import scala.concurrent.{ExecutionContext, Future}
 
// // cors
// import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

// import slick.jdbc.SQLServerProfile.api._
// import java.util.UUID
// import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
// import mvchr.TableResultHelper
// import akka.stream.typed.scaladsl.ActorMaterializer


// case class UserAdded(id: Long, name: String, email: String, timestamp: Long)
// final case class User(id: Long, name: String, email: String)



// trait UserJsonProtocol extends DefaultJsonProtocol {
//     implicit val userAddedFormat = jsonFormat4(UserAdded.apply)
//     implicit val userFormat = jsonFormat3(User.apply)
  
// }

// // trait MainActorSystem {
// //   implicit val system = ActorSystem(Behaviors.empty, "AkkaHttpJson")
// // }

// class AkkaHttpJson extends UserJsonProtocol with SprayJsonSupport with Actor{
    
//     // implicit val userFormat :spray.json.RootJsonFormat[User] = jsonFormat3(User.apply)
//     // implicit val userAddedFormat :spray.json.RootJsonFormat[UserAdded] = jsonFormat4(UserAdded.apply)
//     val db = Database.forConfig("dms")
    
//     val test = get{
//         path("hello"){
//             complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hello akka http"))
//         }
//     }

//     val getUser = get{
//          path("api" / "user" / IntNumber){
//             userid => {
//                 println("get user by id")
//                 TableResultHelper.get(db,userid)
//                 userid match{
//                     // case 1 => complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "get user by id"))
//                     case 1 => complete((userid,"Dave","dmuchiri69@gmail.com"))
//                     case _ => complete(StatusCodes.NotFound)
//                 }
//             }
//         }
//     }

//   //   val createUser = post {
//   //   path("api" / "user") {
//   //     entity(as[User]) {
//   //       user => {
//   //         complete(User(UUID.randomUUID().getLeastSignificantBits(), user.name, user.email, System.currentTimeMillis()))
//   //         println("saved user")
//   //         products.Product(1,"hgh","hfh",67,"utyu")
//   //       }
//   //     }
//   //   }
//   // }

//   //   val updateUser = put {
//   //   path("api" / "user") {
//   //     entity (as[User]) {
//   //       user => {
//   //         println("update user")
//   //         complete(User(user.id, "Testuser", "test@test.com"))
//   //       }
//   //     }
//   //   }
//   // }

//   //   val deleteUser = delete {
//   //   path ("api" / "user" / LongNumber) {
//   //     userid => {
//   //       println(s"user ${userid}")
//   //       complete(User(userid, "Testuser", "test@test.com"))
//   //     }
//   //   }
//   // }

//     val route = cors() {
//     concat(test, getUser)
//     //, createUser, updateUser, deleteUser
//   }

// }

// object Main{
//     def main(args: Array[String]): Unit = {

//     implicit val actorSystem  = ActorSystem("AkkaHttpJson")
//    // implicit val mat = ActorMaterializer()
//      var actor = actorSystem.actorOf(Props[AkkaHttpJson],"PropExample"); 
//    Http().newServerAt("127.0.0.1",8080).bind(AkkaHttpJson.route)

//    }
// }


// // @main def httpserver: Unit =
// // val bindFuture = Http().newServerAt("127.0.0.1",8080).bind(route)


package mvchr.scala

import scala.concurrent.duration._
import akka.actor._
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import mvchr.RestInterface

import com.typesafe.config._

object Main extends App with RestInterface {
  //val appconf = ConfigFactory.parseResources("application.conf")
  val config = ConfigFactory.load()
                          //.withFallback(appconf)
                         // .resolve()
  println("COnfigsss###############################################S====::::"+config)
  val host = config.getString("http.host")  //"127.0.0.1"
  val port = config.getInt("http.port")  //5000

  implicit val system = ActorSystem("scala-REST-webservice")
  //implicit val materializer = ActorMaterializer()


  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  val api = routes
println("API:::::========="+api+" _____________HOST:::::========="+host+"____________PORT:::::========= "+port)
  // Http().newServerAt(handler = api, interface = host, port = port) map { binding =>
  //   println(s"REST interface bound to ${binding.localAddress}") } recover { case ex =>
  //   println(s"REST interface could not bind to $host:$port", ex.getMessage)
  // }

  Http().newServerAt(host, port).bind(api).map(
    binding => println(s"REST interface bound to ${binding.localAddress}")).
    recover{ case ex =>
    println(s"REST interface could not bind to $host:$port", ex.getMessage)
  }
}