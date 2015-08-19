package engine

import java.sql.CallableStatement;
import java.sql.Types;
import play.api.db._
import play.api.Play.current
import scala.util.{Try, Success, Failure}
import model._

object Executor {

  def process(test: Test) = {
    execute[String](test.operationScripts)(executeScript)
      match {
        case Success(r)  => execute[String](test.operationScripts)(executeScript)
                              match {
                                case Success(r)  => ??? //Emits the result to Report
                                case Failure(ex) => ??? //Lauch an error or something else
                              }
        case Failure(ex) => ??? //Lauch an error or something else
      }
  }

  /* If the item list has the same size of the result list
   * then all the items has been successfully executed
   * The takewhile function stops the execution when receives a result false
   */
  def execute[T](l: List[T])(f: (T) => Boolean): Try[Boolean] =
    Try(l.length == (l map f).takeWhile(r => r).length)

  /* This functions is insecure because it access an external platform
   * and does not returns a Try[Boolean] value
   * It is made in this way to keep the execute[T] function more readable and generic
   */
  def executeScript(script: String): Boolean = {
    val conn = DB.getDataSource("oracle").getConnection
    val cs = conn.prepareCall(script);
    try {        
      cs.registerOutParameter(1, Types.BOOLEAN);
      cs.execute
      cs.getBoolean(1)
    } finally {
      cs.close
    }
  }

}