package model

class Test (val operations:  List[Operation],
            val validations: List[Validation],
            val testType: TestType) {

  def operationContents:  List[String] = resolveContents(operations)
  def validationContents: List[String] = resolveContents(validations)
  def contents: List[String] = operationContents ++ validationContents

  private 
    def resolveContents(exeList: List[Executable]): List[String] =
    (for (exe <- exeList) yield exe.contents).foldLeft(List[String]())((acc, l) => acc ++ l)

}

object Test {

  def apply (operations:  List[Operation],
             validations: List[Validation],
             testType: TestType) = new Test(operations, validations, testType)

}