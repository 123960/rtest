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
    test.testType match {
      case DatabaseTest =>  executeWhileTrue(onDatabase)(test.contents)(Contexts.engineDatabaseExecuteContext)
      case HttpTest     =>  executeWhileTrue(onHttp)(test.contents)(Contexts.engineHttpExecuteContext)
      case _            =>  ??? //emits an test failure with TestTypeNotDefiniedException
    }
  }

  /* If the item list has the same size of the result list
   * then all the items has been successfully executed
   * The takewhile function stops the execution when it eceives a result false
   * This behave can be achieved only using a lazy list, because of that the l list is tranformed into a view
   */
  def executeWhileTrue[T](f: (T) => Boolean)(list: List[T])(implicit executionContext: ExecutionContext): Future[Boolean] =
    list match {
      case List() => (Promise[Boolean]() failure (new IllegalArgumentException("An empty list was received"))).future
      case l      => Future((l.length == (l.view map f).takeWhile(r => r).length))(executionContext)
    }
    

  private 
    def onDatabase(content: String): Boolean = {
      val conn = DB.getDataSource("oracle").getConnection
      val cs = conn.prepareCall(content);
      try {        
        cs.registerOutParameter(1, Types.BOOLEAN);
        cs.execute
        cs.getBoolean(1)
      } finally {
        cs.close
      }
    }

  private 
    def onHttp(content: String): Boolean = ???

}