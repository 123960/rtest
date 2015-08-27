package test

import org.scalacheck._
import org.scalacheck.Prop.{forAll}
import org.scalacheck.Gen.{ choose, listOf, nonEmptyListOf, listOfN, oneOf }
import model._

object ExecutableSpec extends Properties("Executable") {

  implicit lazy val arbOperation: Arbitrary[Operation] = Arbitrary(Generators.operationGen)

  property("contents replace using Operation") = forAll { (oper: Operation) =>
    !(oper.contents.view.map(s => s contains "&").takeWhile(r => !r) reduceLeft(_ || _))
  }

}

