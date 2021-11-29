package mvchr.scala

import slick.jdbc.SQLServerProfile.api._
import scala.concurrent.{ExecutionContext, Future}
//import slick.driver.MySQLDriver.api._

object Items {
  val items = TableQuery[Items]
}


case class DBItem(id: Int, description: String, cost: Double,  warehouseLocationId: Int)



class Items(tag: Tag) extends Table[DBItem](tag, "Items") {

  def intoItems(pair: (Int,String, Double, Int)): DBItem =
  DBItem(pair._1, pair._2,pair._3,pair._4)

def fromItems(item: DBItem): Option[(Int,String, Double, Int)] =
  Some((item.id, item.description, item.cost, item.warehouseLocationId))

  def id = column[Int]("Id", O.PrimaryKey, O.AutoInc)
  def description = column[String]("Description")
  def cost = column[Double]("Cost")
  def warehouseLocationId = column[Int]("WarehouseLocationId")
  def * = (id, description, cost, warehouseLocationId) <> (intoItems,fromItems)
}


// class ItemService(implicit val executionContext: ExecutionContext) {
 
//   var Items = Vector.empty[DBItem]

//    def createItem(item: DBItem): Future[Option[String]] = Future {
//     Items.find(_.id == item.id) match {
//       case Some(q) => None // Conflict! id is already taken
//       case None =>
//         Items = Items :+ item
//         Some(item.id.toString())
      
//     }
//   }
// }

