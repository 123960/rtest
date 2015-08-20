package engine

import scala.concurrent._
import play.api.libs.concurrent.Akka
import play.api.Play.current

object Contexts {
  implicit val engineObserverContext: ExecutionContext = Akka.system.dispatchers.lookup("engine-observer-context")
  implicit val engineExecuteContext: ExecutionContext = Akka.system.dispatchers.lookup("engine-execute-context")
}