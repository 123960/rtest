package model

trait Executable {

  def contents: List[String]

  def resolveContent(contentBase: String, characList: List[Characteristic]): String =
    characList.foldLeft (contentBase)((acc, charac) => acc.replaceAll(charac.key, charac.value))

}

case class Operation (val contentBase: String, val characteristics: List[Characteristic])
  extends Executable {

  def contents: List[String] = {
    for (i <- 0 until 1) //Represents some factor of randomness
      yield resolveContent(contentBase, characteristics)
  } toList

}

case class Validation (val contentBase: String, val characteristics: List[Characteristic])
  extends Executable {

    def contents: List[String] = List(resolveContent(contentBase, characteristics))

}