package com.bulavskiy.handling.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.bulavskiy.handling.composite.SymbolLeaf;
import com.bulavskiy.handling.composite.TextComponent;
import com.bulavskiy.handling.composite.TextComposite;
import com.bulavskiy.handling.composite.TextType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexemeToWordSymbolParser extends AbstractTextParser {

  private static final Logger log = LogManager.getLogger();
  private static final Pattern PATTERN = Pattern.compile(TextRegex.WORD_PUNCTUATION_REGEX);
  private static final String WORDS_OR_NUMBERS = "[\\p{L}0-9]+";

  @Override
  public TextComponent parse(String lexemeString) {
    TextComponent lexemeComposite = new TextComposite(TextType.LEXEME);
    Matcher matcher = PATTERN.matcher(lexemeString);

    while (matcher.find()) {
      String componentString = matcher.group();
      TextType componentType;

      if (componentString.matches(WORDS_OR_NUMBERS)) {
        componentType = TextType.WORD;
      } else {
        componentType = TextType.PUNCTUATION;
      }

      TextComponent subComposite = new TextComposite(componentType);

      for (char symbol : componentString.toCharArray()) {
        subComposite.add(new SymbolLeaf(symbol, TextType.SYMBOL));
        log.info("Symbols was added");
      }

      lexemeComposite.add(subComposite);
    }

    return lexemeComposite;
  }
}