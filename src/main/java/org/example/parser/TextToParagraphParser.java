package org.example.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.composite.TextComponent;
import org.example.composite.TextComposite;
import org.example.composite.TextType;

public class TextToParagraphParser extends AbstractTextParser{
  private static final Logger log = LogManager.getLogger();

  @Override
  public TextComponent parse(String text) {
    TextComponent textComposite = new TextComposite(TextType.TEXT);

    String[] paragraphs = text.strip().split(TextRegex.PARAGRAPH_DELIMITER_REGEX);
    log.info("The paragraphs were separated");

    for (String paragraph : paragraphs){
      if(nextParser != null){
        TextComponent paragraphComponent = nextParser.parse(paragraph);
        log.info("Create next pasrser.");

        textComposite.add(paragraphComponent);
        log.info("Paragraph was added");
      }
    }
    return textComposite;
  }
}
