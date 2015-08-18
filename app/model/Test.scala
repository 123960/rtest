package model

class Test (val operations:  List[Operation],
            val validations: List[Validation]) {

  def operationScripts: List[String] = (for (oper <- operations.par) yield oper.scripts).reduceLeft(_ ++ _)

}

object Test {

  def apply (operations:  List[Operation],
             validations: List[Validation]) = new Test(operations, validations)

}