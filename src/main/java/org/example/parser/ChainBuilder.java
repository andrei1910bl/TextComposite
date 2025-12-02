package org.example.parser;

public class ChainBuilder {
  public AbstractTextParser buildChain(){
    AbstractTextParser textToParagraph = new TextToParagraphParser();
    AbstractTextParser paragraphToSentence = new ParagraphToSentenceParser();
    AbstractTextParser sentenceToLexeme = new SentenceToLexemeParser();
    AbstractTextParser lexemeToWordSymbol = new LexemeToWordSymbolParser();

    textToParagraph.setNextParser(paragraphToSentence);
    paragraphToSentence.setNextParser(sentenceToLexeme);
    sentenceToLexeme.setNextParser(lexemeToWordSymbol);
    return sentenceToLexeme;
  }
}
