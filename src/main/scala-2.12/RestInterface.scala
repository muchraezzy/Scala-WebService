package mvchr

import concurrent.ExecutionContext

import akka.http.scaladsl.server.Route

import mvchr.resources.ItemResource
import mvchr.services.ItemService

trait RestInterface extends Resources {

  implicit def executionContext: ExecutionContext

  lazy val itemService = new ItemService

  val routes: Route = itemRoutes

}

trait Resources extends ItemResource