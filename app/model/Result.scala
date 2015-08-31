package model

class Result(val test: Test, val result: Boolean) {
  override def toString(): String =  "Result of Test " ++ test.toString  
}

object Result {
  def apply(test: Test, result: Boolean) = new Result(test, result)
}