package mvchr.services
import scala.concurrent.duration._
import scala.async.Async.{async, await}
import mvchr.entities.{Itemss, ItemsUpdate}
import slick.jdbc.SQLServerProfile.api._
import scala.concurrent.{ExecutionContext, Future, Await}
import mvchr.scala._

class ItemService(implicit val executionContext: ExecutionContext) {

  lazy val itemms = TableQuery[Items]
  var items = Vector.empty[DBItem]
  val db = Database.forConfig("dms")

  def createItem(item: DBItem): Future[Option[Int]] = Future {
      // val q = for { p <- Items.items if p.id === item.id } yield p
      // val exists : Future[Seq[DBItem]] = db.run(q.result)
      // val sqlData = Await.result(exists,???)
      // val item = sqlData.head
      val id = item.id

      val found = db.run(itemms.filter(_.id === id)
        .take(1)
        .result
        .headOption)
      
        val warehse = Await.result(found,???).map(x=>x.warehouseLocationId).get
      Some(warehse)

    // items.find(_.id == item.id) match {
    //   case Some(q) => None // Conflict! id is already taken
    //   case None =>
    //      val action :DBIO[Int] =(Items.items returning Items.items.map(c=>(c.id))) +=
    //       DBItem(item.id, item.description, item.cost, item.warehouseLocationId)
    
    //       lazy val insertitem =itemms returning itemms.map(_.id)
    //       lazy val ins :DBIO[Int] = insertitem += item
    // //   // val action = insertitem += Items(1,item.description,item.cost,item.warehouseLocationId)
    //       db.run(ins)
    //       val futureDB : Future[Int] = db.run(action)
    // //       async {
    //       val savedItemId = Await.result(futureDB,???)
    //      // val savedid = savedItemId.map(x=>x).
    // //    println(s"saveItem ==========="+savedItemId+"_____DB.Data::::+++====== "+futureDB+"item===  "+item+"insertitem   ===="+insertitem)
    // //   println(s"TableResultRunner.saveItem() savedItem.Id ${savedItemId}")
    // // }

    //  // items = items :+ item
    //   //Some(item.id)
    //     Some(savedItemId)
    // }
    //Some(99999999)
  }

  def getItem(id: Int): Future[Option[DBItem]] = Future {
    items.find(_.id == id)
  }

  def updateItem(id: Int, update: ItemsUpdate): Future[Option[DBItem]] = {

    def updateEntity(item: DBItem): DBItem = {
      val description = update.description.getOrElse(item.description)
      val cost = update.cost.getOrElse(item.cost)
      val warehouseLocationId = update.warehouseLocationId.getOrElse(item.warehouseLocationId)
      DBItem(id, description, cost, warehouseLocationId)
    }

    getItem(id).flatMap { maybeItem =>
      maybeItem match {
        case None => Future { None } // No question found, nothing to update
        case Some(item) =>
          val updatedItem = updateEntity(item)
          deleteItem(id).flatMap { _ =>
            createItem(updatedItem).map(_ => Some(updatedItem))
          }
      }
    }
  }

  def deleteItem(id: Int): Future[Unit] = Future {
    items = items.filterNot(_.id == id)
  }


  def test(): Future[Option[String]] = Future {
    Some("Test Complete!")
  }

}
