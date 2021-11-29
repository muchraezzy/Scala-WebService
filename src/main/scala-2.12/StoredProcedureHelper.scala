package mvchr.scala

import java.sql.ResultSet
import slick.jdbc.SQLServerProfile.api._
//import slick.driver.MySQLDriver.api._
import slick.ast.Type
import java.sql.Types

object StoredProcedureHelper {

  def selectItems(db: Database, description: String): Unit = {

    println(s"Description is:====="+ description+" ,DB IS :======"+db.source)

    val sqlStatement = db.source.createConnection().prepareCall(
      "{ call EXEC [dbo].[sp_SelectItemsByDescription] (?) }",
      ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

       sqlStatement.setFetchDirection(ResultSet.FETCH_FORWARD);
    sqlStatement.setString(1,description)
    // sqlStatement.registerOutParameter(2, Types.INTEGER);
    //sqlStatement.setInt(2,1)
      println("sqlStatement::::___----"+sqlStatement)
   

    val rs = sqlStatement.executeQuery()

    while (rs.next()) {
      val item = new DBItem(
        rs.getInt("Id"),
        rs.getString("Description"),
        rs.getDouble("Cost"),
        rs.getInt("WarehouseLocationId"))

      println(s"StoredProcedureHelper.selectProducts " +
        "using description set to ${desc} got this result : " +
        s"Id: ${item.id}, Description: ${item.description}, " +
        s"Cost: ${item.cost}, WarehouseLocationId: ${item.warehouseLocationId}")
    }

    rs.close()
    sqlStatement.close()
  }
}

