package model

class Operation (val scriptBase: String, val characteristics: List[Characteristic]) {

  def scripts: List[String] = {
    for (i <- 0 until 1) //Represents some factor of randomness
      yield 
        characteristics.par.foldLeft (scriptBase)((acc, charac) => acc.replaceAll(charac.key, charac.value))
  } toList

}