package com.bulavskiy.handling.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.bulavskiy.handling.composite.TextComponent;
import com.bulavskiy.handling.composite.TextComposite;
import com.bulavskiy.handling.composite.TextType;
import com.bulavskiy.handling.service.TextSortingService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TextSortingServiceImpl implements TextSortingService {

  private static final Logger log = LogManager.getLogger();

  @Override
  public String sortSentencesByLexemeCount(TextComponent textComposite) {
    List<TextComponent> sentences = extractComponents(textComposite, TextType.SENTENCE);

    sentences.sort(Comparator.comparingInt(this::countLexemes));
    log.info("Sentences sorted by lexeme count.");

    StringBuilder result = new StringBuilder();
    for (TextComponent sentence : sentences) {
      result.append(sentence.toString()).append(" ");
    }

    log.info("Sorting finished. Text reconstructed.");
    return result.toString().trim();
  }

  private int countLexemes(TextComponent sentenceComponent) {
    int count = 0;
    if (sentenceComponent instanceof TextComposite) {
      List<TextComponent> children = ((TextComposite) sentenceComponent).getComponents();
      for (TextComponent child : children) {
        if (child.getType() == TextType.LEXEME) {
          count++;
        }
      }
    }
    return count;
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