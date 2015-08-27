package model

class Result(val test: Test, val result: Boolean)

object Result {
  def apply(test: Test, result: Boolean) = new Result(test, result)
}