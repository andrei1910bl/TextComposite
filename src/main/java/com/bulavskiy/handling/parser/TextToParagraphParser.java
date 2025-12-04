package com.bulavskiy.handling.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.bulavskiy.handling.composite.TextComponent;
import com.bulavskiy.handling.composite.TextComposite;
import com.bulavskiy.handling.composite.TextType;

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
