package com.bulavskiy.handling.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.bulavskiy.handling.composite.TextComponent;
import com.bulavskiy.handling.composite.TextComposite;
import com.bulavskiy.handling.composite.TextType;
import com.bulavskiy.handling.service.TextModificationService;

import java.util.ArrayList;
import java.util.List;

public class TextModificationServiceImpl implements TextModificationService {

  private static final Logger log = LogManager.getLogger();

  @Override
  public void swapFirstAndLastWords(TextComponent textComposite) {
    List<TextComponent> sentences = extractComponents(textComposite, TextType.SENTENCE);

    for (TextComponent sentence : sentences) {
      if (!(sentence instanceof TextComposite)) {
        continue;
      }
      TextComposite sentenceComposite = (TextComposite) sentence;

      List<TextComponent> lexemes = sentenceComposite.getComponents();

      int firstWordIndex = findFirstOrLastWordIndex(lexemes, true);
      int lastWordIndex = findFirstOrLastWordIndex(lexemes, false);

      if (firstWordIndex != -1 && lastWordIndex != -1 && firstWordIndex != lastWordIndex) {
        TextComponent first = lexemes.get(firstWordIndex);
        TextComponent last = lexemes.get(lastWordIndex);

        lexemes.set(firstWordIndex, last);
        lexemes.set(lastWordIndex, first);
        log.info("Swapped words in sentence: {}", sentence.toString());
      } else {
        log.info("Skipping swap in a sentence (not enough words or words are the same): {}", sentence.toString());
      }
    }

    log.info("Modification finished.");
  }

  private int findFirstOrLastWordIndex(List<TextComponent> lexemes, boolean isFirst) {
    int start = isFirst ? 0 : lexemes.size() - 1;
    int end = isFirst ? lexemes.size() : -1;
    int step = isFirst ? 1 : -1;

    for (int i = start; i != end; i += step) {
      if (containsWord(lexemes.get(i))) {
        return i;
      }
    }
    return -1;
  }

  private boolean containsWord(TextComponent lexemeComponent) {
    if (!(lexemeComponent instanceof TextComposite)) {
      return false;
    }
    for (TextComponent child : ((TextComposite) lexemeComponent).getComponents()) {
      if (child.getType() == TextType.WORD) {
        return true;
      }
    }
    return false;
  }

  private List<TextComponent> extractComponents(TextComponent component, TextType type) {
    List<TextComponent> result = new ArrayList<>();
    if (component.getType() == type) {
      result.add(component);
    } else if (component instanceof TextComposite) {
      for (TextComponent child : ((TextComposite) component).getComponents()) {
        result.addAll(extractComponents(child, type));
      }
    }
    return result;
  }
}