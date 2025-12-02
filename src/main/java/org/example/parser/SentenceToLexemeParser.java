package org.example.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.composite.TextComponent;
import org.example.composite.TextComposite;
import org.example.composite.TextType;

public class SentenceToLexemeParser extends AbstractTextParser{
  private static final Logger log = LogManager.getLogger();
  @Override
  public TextComponent parse(String sentenceString) {

    TextComponent sentenceComposite = new TextComposite(TextType.SENTENCE);

    String[] lexemes = sentenceString.strip().split(TextRegex.LEXEME_DELIMITER_REGEX);
    log.info("The lexemes were separated : {}", lexemes[6]);

    for(String lexeme : lexemes){
      if(nextParser != null){
        TextComponent lexemeComponent = nextParser.parse(lexeme);
        log.info("Create next parser");

        sentenceComposite.add(lexemeComponent);
        log.info("Lexeme was added");
      }
    }
    return sentenceComposite;
  }
}
