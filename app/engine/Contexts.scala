package engine

import scala.concurrent._
import play.api.libs.concurrent.Akka
import play.api.Play.current

object Contexts {
  implicit val engineObserverContext: ExecutionContext = Akka.system.dispatchers.lookup("engine-observer-context")
  implicit val engineDatabaseExecuteContext: ExecutionContext = Akka.system.dispatchers.lookup("engine-database-execute-context")
  implicit val engineHttpExecuteContext: ExecutionContext = Akka.system.dispatchers.lookup("engine-http-execute-context")
}