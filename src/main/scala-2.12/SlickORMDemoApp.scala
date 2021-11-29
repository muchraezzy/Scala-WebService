package mvchr.scala

import slick.jdbc.SQLServerProfile.api._

object SlickORMDemoApp {


  val counter = 0
  val db = Database.forConfig("dms")

  // PLAIN SQL QUERIES
  PlainSQLHelper.selectScalarObject(db)
  PlainSQLHelper.selectTupleObject(db)
  PlainSQLHelper.selectRawTableObject(db)

  // INSERT multiple
  val itemsToInsert = List(
    DBItem(-1, "coffee", 200, 1),
    DBItem(-1, "beans", 90, 1))
  TableResultHelper.insertSeveralItems(db, itemsToInsert)

  //INSERT single
 // TableResultHelper.saveItem(db, DBItem(-1, "milk", 60, 1))
  TableResultHelper.saveItem(db, DBItem(-1, "milk", 60, 1))
  // SELECT
  TableResultHelper.selectTwoItems(db)

  // DELETE RANDOM
  TableResultHelper.deleteItemById(db,1)

  // UPDATE
  TableResultHelper.updateItemCost(db, "milk", 80)

  // GET BY ID
  TableResultHelper.findItemById(db, 1)


  //STORE PROC TEST
  StoredProcedureHelper.selectItems(db, "milk")

  System.in.read()
  ()

}

