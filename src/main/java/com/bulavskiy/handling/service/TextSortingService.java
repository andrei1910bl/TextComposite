package com.bulavskiy.handling.service;

import com.bulavskiy.handling.composite.TextComponent;

public interface TextSortingService {
  String sortSentencesByLexemeCount(TextComponent textComposite);
}
