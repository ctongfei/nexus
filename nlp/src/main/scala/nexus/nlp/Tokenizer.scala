package nexus.nlp

/**
 * @author Tongfei Chen
 */
trait Tokenizer {

  def tokenize(s: String): IndexedSeq[String]

}

object Tokenizer {

  object Whitespace extends Tokenizer {
    def tokenize(s: String) = s.split("\\s+")
  }

}
