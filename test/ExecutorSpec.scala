import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import scala.concurrent._
import ExecutionContext.Implicits.global
import engine._
import model._
import scala.concurrent.duration._

object ExecutorSpec extends Properties("Executor") {

  property("execute laziness and breakness for an no-empty list") = forAll { (l: List[Boolean]) =>
    val l2 = l ++ List(false)
    var i = 0
    def f(b: Boolean) = {i += 1; b}
    val future = Executor.execute(f)(l2)
    Await.result(future, 10 seconds)
    l2.length > i
  }

  property("execute exception for an empty list") = ???

  //property("process database Test") = { 
  //  val script = "BEGIN SELECT 1 INTO ? FROM DUAL; END;"
  //  val t = Test(operations  = List(Operation(contentBase = script, characteristics = List(),
  //               validations = List(),
  //               testType    = TestType.DatabaseTest)))
  //  Executor.process(t)
  //}

}