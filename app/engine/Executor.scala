package engine

import java.sql.{CallableStatement, Types}
import play.api.db._
import play.api.Play.current
import scala.concurrent._
import scala.util.{Try, Success, Failure}
import model._
import controller._

object Executor {

  def start = ExecuteController.observable.subscribe ({
    test => Future(process(test))(Contexts.engineObserverContext)
  })

  def process(test: Test) = {
    execute(onDatabase)(test.operationScripts)
    //execute(onDatabase)(test.validationScripts)
    //emitreport
  }

  /* If the item list has the same size of the result list
   * then all the items has been successfully executed
   * The takewhile function stops the execution when it eceives a result false
   * This behave can be achieved only using a lazy list, because of that the l list is tranformed into a view
   */
  def execute[T](f: (T) => Boolean)(l: List[T]): Future[Boolean] =
    Future((l.length == (l.view map f).takeWhile(r => r).length))(Contexts.engineExecuteContext)

  /* This functions is insecure because it access an external platform
   * and does not returns a Try[Boolean] value
   * It is made in this way to keep the execute[T] function more readable and generic
   * Use the execute[T] function instead
   */
  private 
    def onDatabase(script: String): Boolean = {
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