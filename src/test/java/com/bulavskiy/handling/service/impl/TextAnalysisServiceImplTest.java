package com.bulavskiy.handling.service.impl;

import com.bulavskiy.handling.composite.SymbolLeaf;
import com.bulavskiy.handling.composite.TextComponent;
import com.bulavskiy.handling.composite.TextComposite;
import com.bulavskiy.handling.composite.TextType;
import com.bulavskiy.handling.service.TextAnalysisService;
import com.bulavskiy.handling.service.impl.TextAnalysisServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextAnalysisServiceImplTest {

  private TextAnalysisService analysisService;

  @BeforeEach
  void setUp() {
    analysisService = new TextAnalysisServiceImpl();
  }

  private TextComposite createWord(String wordValue) {
    TextComposite wordComp = new TextComposite(TextType.WORD);
    for (char c : wordValue.toCharArray()) {
      wordComp.add(new SymbolLeaf(c, TextType.SYMBOL));
    }
    return wordComp;
  }

  private TextComposite createLexeme(String wordValue) {
    TextComposite lexemeComp = new TextComposite(TextType.LEXEME);
    lexemeComp.add(createWord(wordValue));
    return lexemeComp;
  }

  private TextComponent createSentence(String... words) {
    TextComposite sentenceComp = new TextComposite(TextType.SENTENCE);
    for (String word : words) {
      sentenceComp.add(createLexeme(word));
    }
    return sentenceComp;
  }

  private TextComponent createTestTextStructure(TextComponent... sentences) {
    TextComposite paragraphComp = new TextComposite(TextType.PARAGRAPH);
    for (TextComponent sentence : sentences) {
      paragraphComp.add(sentence);
    }

    TextComposite textComp = new TextComposite(TextType.TEXT);
    textComp.add(paragraphComp);
    return textComp;
  }

  @Test
  void findMaxSentencesWithCommonWord_CommonWordExists_ReturnsMaxCount() {
    TextComponent s1 = createSentence("Hello", "world", "is", "red");
    TextComponent s2 = createSentence("The", "apple", "Is", "good");
    TextComponent s3 = createSentence("This", "Is", "awesome");

    TextComponent testText = createTestTextStructure(s1, s2, s3);

    int expected = 3;
    int actual = analysisService.findMaxSentencesWithCommonWord(testText);

    assertEquals(expected, actual, "Should find the word 'is' common to 3 sentences.");
  }

  @Test
  void findMaxSentencesWithCommonWord_EmptyText_ReturnsZero() {
    TextComponent testText = new TextComposite(TextType.TEXT);
    int expected = 0;
    int actual = analysisService.findMaxSentencesWithCommonWord(testText);

    assertEquals(expected, actual, "Should return 0 for an empty text structure.");
  }
}