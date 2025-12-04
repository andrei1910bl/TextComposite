package com.bulavskiy.handling.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.bulavskiy.handling.composite.TextComponent;
import com.bulavskiy.handling.composite.TextComposite;
import com.bulavskiy.handling.composite.TextType;

public class SentenceToLexemeParser extends AbstractTextParser{
  private static final Logger log = LogManager.getLogger();
  @Override
  public TextComponent parse(String sentenceString) {

    TextComponent sentenceComposite = new TextComposite(TextType.SENTENCE);

    String[] lexemes = sentenceString.strip().split(TextRegex.LEXEME_DELIMITER_REGEX);
    log.info("The lexemes were separated");

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
