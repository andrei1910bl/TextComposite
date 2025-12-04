package com.bulavskiy.handling.service;

import com.bulavskiy.handling.composite.TextComponent;

public interface TextAnalysisService {
  int findMaxSentencesWithCommonWord(TextComponent textComposite);
}
