package mvchr.scala

import slick.jdbc.SQLServerProfile.api._
//import slick.driver.MySQLDriver.api._
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async.{async, await}

object TableResultHelper {

  def selectTwoItems(db: Database) : Unit = {

    implicit val session: Session = db.createSession()
    val q =  Items.items.take(2)
    val futureDB : Future[Seq[DBItem]] = db.run(q.result)

    async {
      val sqlData = await(futureDB)
      val item = sqlData.head
      println(s"TableResultRunner.selectTwoItems()[0] " +
        s"Id: ${item.id}, Description: ${item.description}, " +
        s"Cost: ${item.cost}, WarehouseLocationId: ${item.warehouseLocationId}")
    } onComplete {
      case e => {
        println(s"ERROR ---selectTwoItems: $e")
      }
    }
  }

  def insertSeveralItems(db: Database, items : List[DBItem]) : Unit = {

    implicit val session: Session = db.createSession()
    val insertActions = DBIO.seq(
      (Items.items ++= items.toSeq).transactionally
    )
    val sql = Items.items.insertStatement
    val futureDB : Future[Unit] = db.run(insertActions)

    async {
      await(futureDB)
      println(s"TableResultRunner.insertSeveralItems() DONE")
    } onComplete {
      case e => {
        println(s"ERROR --insertSeveralItems: $e")
      }
    }
  }

  def saveItem(db: Database, item: DBItem) = {

   lazy val itemms = TableQuery[Items]
    val action :DBIO[Int] =(Items.items returning Items.items.map(c=>(c.id))) +=
      DBItem(item.id, item.description, item.cost, item.warehouseLocationId)
    
  lazy val insertitem =itemms returning itemms.map(_.id)
  lazy val ins :DBIO[Int] = insertitem += item
 // val action = insertitem += Items(1,item.description,item.cost,item.warehouseLocationId)
    db.run(ins)
    val futureDB : Future[Int] = db.run(action)

    println(s"_____DB.Data::::+++====== "+futureDB+"item===  "+item+"insertitem   ===="+ins.getDumpInfo+"_______Action:::::======"+action.getDumpInfo)

    async {
      val savedItemId = await(futureDB)
       println(s"saveItem ==========="+savedItemId+"_____DB.Data::::+++====== "+futureDB+"item===  "+item+"insertitem   ===="+insertitem)
      println(s"TableResultRunner.saveItem() savedItem.Id ${savedItemId}")
    } onComplete {
      case e => {
        println(s"ERROR --saveItem: $e")
      }
    }
  }

  def deleteItemById(db: Database,id : Int) = {

    async {
      val q =  Items.items.take(id)
      val futureDB : Future[Seq[DBItem]] = db.run(q.result)
      val sqlData = await(futureDB)
      val item = sqlData.head
      val deleteFuture : Future[Unit] = db.run(
        Items.items.filter(_.id === item.id).delete).map(_ => ())
      await(deleteFuture)
      println(s"TableResultRunner.deleteItemById() deleted item.Id ${item.id}")
    } onComplete {
      case e => {
        println(s"ERROR --deleteItemById: $e")
      }
    }
  }


  def updateItemCost(db: Database, description : String, cost : Double) = {

    async {
      val q = Items.items
        .filter(_.description === description)
        .map(_.cost)
        .update(cost)

      val futureDB = db.run(q)
      val done = await(futureDB)
      println(s"Update cost of ${description}, to ${cost}")

      val q2 = for { p <- Items.items if p.description === description } yield p
      val futureDBQuery : Future[Seq[DBItem]] = db.run(q2.result)
      val items = await(futureDBQuery)
      items.map(item => println(s"TableResultRunner.updateItemCost The item is now $item") )
    } onComplete {
      case e => {
        println(s"ERROR --updateItemCost: $e")
      }
    }
  }


  def findItemById(db: Database,id : Int) = {

    async {
      val q = for { p <- Items.items if p.id === id } yield p
      val futureDBQuery : Future[Option[DBItem]] = db.run(q.result.headOption)
      val item : Option[DBItem] = await(futureDBQuery)
      println(s"OPTION ${item}")
      item match {
        case Some(x) =>  println(s"TableResultRunner.findItemById The item is $x")
        case _ => ()
      }
    } onComplete {
      case e => {
        println(s"ERROR --findItemById: $e")
      }
    }
  }

     val itemQuery: TableQuery[Items] = TableQuery[Items]

 def insert(db: Database,item: DBItem): Future[Int] =
  db.run(itemQuery += item)

 def get(db: Database,id: Int): Future[Option[DBItem]] =
  db.run(
   itemQuery
    .filter(_.id === id)
    .take(1)
    .result
    .headOption)

 def update(db: Database,id: Int, description: String, cost: Double,warehouseLocationId: Int ): Future[Int] =
  db.run(
   itemQuery
    .filter(_.id === id)
    .map(x => (x.description,x.cost,x.warehouseLocationId))
    .update(description,cost,warehouseLocationId))

 def delete(db: Database,id: Int): Future[Int] =
  db.run(itemQuery.filter(_.id === id).delete) 

}


class ItemProfile{



}



