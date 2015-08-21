package model

class Operation (val contentBase: String, val characteristics: List[Characteristic]) {

  def contents: List[String] = {
    for (i <- 0 until 1) //Represents some factor of randomness
      yield 
        characteristics.foldLeft (contentBase)((acc, charac) => acc.replaceAll(charac.key, charac.value))
  } toList

}
object Operation {
  def apply (contentBase: String, characteristics: List[Characteristic]) = new Operation(contentBase, characteristics)
}