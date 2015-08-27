package test

import org.scalacheck.Properties
import org.scalacheck.Prop._
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Try, Success, Failure}
import engine._
import model._
import ExecutionContext.Implicits.global

object ExecutorSpec extends Properties("Executor") {

  property("executeWhileTrue laziness and breakness") = forAll { (l: List[Boolean]) =>
    var i = 0
    def f(b: Boolean) = {i += 1; b}
    val future = Executor.executeWhileTrue(f)(l)
    val executeTry = Try(Await.result(future, 10 seconds))
    executeTry match {
      case Success(r)  => l.length match {
                            case 1 => i == 1
                            case n => if (l.reduceLeft (_ && _) ||
                                         (l.dropRight(1).reduceLeft (_ && _))) l.length == i
                                      else l.length > i
                          }
      case Failure(ex) => ex match {
                            case e: IllegalArgumentException => l.isEmpty
                            case _ => false
                          }
    }
  }

}