package com.bulavskiy.handling.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.bulavskiy.handling.composite.TextComponent;
import com.bulavskiy.handling.composite.TextComposite;
import com.bulavskiy.handling.composite.TextType;

public class ParagraphToSentenceParser extends AbstractTextParser{
  private static final Logger log = LogManager.getLogger();

  @Override
  public TextComponent parse(String paragraphString) {
    TextComponent paragraphComposite = new TextComposite(TextType.PARAGRAPH);

    String[] sentences = paragraphString.strip().split(TextRegex.SENTENCE_DELIMITER_REGEX);
    log.info("The sentences were separated : ");

    for (String sentence : sentences) {
      if(nextParser != null) {
        TextComponent sentenceComponent = nextParser.parse(sentence);
        log.info("Create next pasrser.");

        paragraphComposite.add(sentenceComponent);
        log.info("Sentence was added");
      }
    }
    return paragraphComposite;
  }
}
