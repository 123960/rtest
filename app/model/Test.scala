package model

class Test (val operations:  List[Operation],
            val validations: List[Validation],
            val testType: TestType) {

  def operationContents:  List[String] = (for (oper <- operations.par) yield oper.contents).reduceLeft(_ ++ _)
  def validationContents: List[String] = ???

}

object Test {

  def apply (operations:  List[Operation],
             validations: List[Validation],
             testType: TestType) = new Test(operations, validations, testType)

}