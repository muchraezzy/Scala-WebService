package mvchr.resources

import akka.http.scaladsl.server.Route

import mvchr.entities.{Itemss, ItemsUpdate}
import mvchr.routing.MyResource
import mvchr.services.ItemService
import akka.http.scaladsl.model._
import mvchr.scala._

trait ItemResource extends MyResource {

  val itemService: ItemService

  def itemRoutes: Route = pathPrefix("items") {
    pathEnd {
      post {
        entity(as[DBItem]) { item =>
          completeWithLocationHeader(
            resourceId = itemService.createItem(item),
            ifDefinedStatus = 201, ifEmptyStatus = 409)
          }

           //complete(itemService.test())
        // val entity =
        //     HttpEntity(ContentTypes.`application/json`, "Illegal group name, only accept alpha and numeric.")
        //   complete(StatusCodes.NotAcceptable -> entity)
          
        }
    } ~
    path(Segment) { id =>
      get {
        complete(itemService.getItem(id.toInt))
      } ~
      put {
        entity(as[ItemsUpdate]) { update =>
          complete(itemService.updateItem(id.toInt, update))
        }
      } ~
      delete {
        complete(itemService.deleteItem(id.toInt))
      } ~
      post {
        complete(itemService.test())
      }
    }

  }
}