package test

import org.scalacheck._
import model._
import scala.util._

object Generators {

  lazy val testGen: Gen[model.Test] = {
    val operation = operationGen.sample.get
    model.Test("Teste " + Random.nextInt, List(operation), List(), DummyTest)
  }

  lazy val operationGen: Gen[Operation] = {
    val stringList  = {for (i <- 0 to Random.nextInt(100)) yield Random.nextInt(10000)} toList
    val characList  = {for (i <- 0 to Random.nextInt(15)) yield testCharacteristicGen.sample.get} toList
    val finalString = (merge(stringList, characList.map(op => op.key))) mkString " "
    Operation(finalString, characList)
  }

  lazy val testCharacteristicGen: Gen[TestCharacteristic] = new TestCharacteristic()

  def merge[T](list1: List[T], list2: List[T]): List[T] = list1 match {
    case List() => list2
    case head :: tail => head :: merge(list2, tail)
  }

}

class TestCharacteristic extends Characteristic { 

  def key: String   = _key
  def value: String = _value

  lazy val _key   :String = "&" + Random.nextInt(10000)
  lazy val _value :String = Random.nextInt(10000).toString

}