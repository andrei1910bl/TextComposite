package com.bulavskiy.handling.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.bulavskiy.handling.composite.TextComponent;
import com.bulavskiy.handling.composite.TextComposite;
import com.bulavskiy.handling.composite.TextType;
import com.bulavskiy.handling.service.TextAnalysisService;

import java.util.*;

public class TextAnalysisServiceImpl implements TextAnalysisService {

  private static final Logger log = LogManager.getLogger();

  @Override
  public int findMaxSentencesWithCommonWord(TextComponent textComposite) {
    List<TextComponent> sentences = extractComponents(textComposite, TextType.SENTENCE);
    log.debug("Total sentences found: {}", sentences.size());

    Map<String, Set<TextComponent>> wordToSentenceMap = new HashMap<>();

    for (TextComponent sentence : sentences) {
      List<TextComponent> words = extractComponents(sentence, TextType.WORD);
      Set<String> uniqueWordsInSentence = new HashSet<>();

      for (TextComponent word : words) {
        String wordStr = word.toString().toLowerCase();
        uniqueWordsInSentence.add(wordStr);
      }

      for (String uniqueWord : uniqueWordsInSentence) {
        wordToSentenceMap
                .computeIfAbsent(uniqueWord, k -> new HashSet<>())
                .add(sentence);
      }
    }
    log.info("Word mapping complete. Total unique words: {}", wordToSentenceMap.size());

    int maxCount = 0;
    for (Set<TextComponent> sentencesSet : wordToSentenceMap.values()) {
      maxCount = Math.max(maxCount, sentencesSet.size());
    }

    log.info("Analysis finished. Max count of sentences sharing a word: {}", maxCount);
    return maxCount;
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