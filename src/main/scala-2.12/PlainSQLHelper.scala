package mvchr.scala

import slick.jdbc.SQLServerProfile.api._
//import slick.driver.MySQLDriver.api._
import scala.collection.immutable
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async.{async, await}

object PlainSQLHelper {

  def selectScalarObject(db:Database) : Unit = {

    val action = sql"""Select count(*) as 'sysobjectsCount'  from sysobjects""".as[Int]
    val futureDB : Future[Vector[Int]] = db.run(action)

    async {
      val sqlData = await(futureDB)
      val count = sqlData.head
      println(s"PlainSQLHelper.selectScalarObject() sysobjectsCount: $count")
    } onComplete {
      case e => {
        println(s"ERROR --selectScalarObject: $e")
      }
    }
  }


  def selectTupleObject(db: Database) : Unit = {

    val action = sql"""Select count(*)  as 'sysobjectsCount', count(*)/10  as 'sysobjectsCountDiv10' from sysobjects""".as[(Int,Int)]
    val futureDB : Future[Vector[(Int,Int)]] = db.run(action)

    async {
      val sqlData = await(futureDB)
      val (x,y) = sqlData.head
      println(s"HEEEEEEEERRRRRRRREEEEEEEEEEEEEEE ==========="+sqlData+"_____DB.Data::::+++====== "+futureDB+"xxxxxxxxx===  "+x+"yyyyyyy   ===="+y+"______Action:::::===="+action.statements)
      println(s"PlainSQLHelper.selectTupleObject() sysobjectsCount: $x, sysobjectsCountDiv10: $y")
    } onComplete {
      case e => {
        println(s"ERROR --selectTupleObject: $e")
      }
    }
  }


  def selectRawTableObject(db: Database) : Unit = {

    val action = sql"""Select * from Items""".as[(Int,String, Double, Int)]
    val futureDB : Future[Vector[(Int,String, Double, Int)]] = db.run(action)

    async {
      val sqlData = await(futureDB)
     
      val item = RawSQLItem(0,"Null", 0.00, 0)
      if(!sqlData.isEmpty){
       val (id,desc, cost, location) = sqlData.head
       val item = RawSQLItem(id,desc, cost, location)
        println("NON EMPTY RESULT SET")
      }else{
        val (id,desc, cost, location) = (0,"Null", 0.00, 0)
        val item = RawSQLItem(id,desc, cost, location)
      }
     
      
      println(s"HEEEEEEEERRRRRRRREEEEEEEEEEEEEEE ==========="+sqlData+"_____DB.Data::::+++====== "+futureDB+"item===  "+item+" idddd   ===="+item.id)
      println(s"PlainSQLHelper.selectRawTableObject() Id: ${item.id}, Description: ${item.description}, Cost: ${item.cost}, WarehouseLocation: ${item.warehouseLocationId}")
    } onComplete {
      case e => {
        println(s"ERROR --selectRawTableObject: $e")
      }
    }
  }


  case class RawSQLItem(id: Int, description: String, cost: Double,  warehouseLocationId: Int)

}
