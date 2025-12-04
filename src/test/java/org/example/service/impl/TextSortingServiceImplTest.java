package org.example.service.impl;

import org.example.composite.SymbolLeaf;
import org.example.composite.TextComponent;
import org.example.composite.TextComposite;
import org.example.composite.TextType;
import org.example.service.TextSortingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextSortingServiceImplTest {

  private TextSortingService sortingService;

  @BeforeEach
  void setUp() {
    sortingService = new TextSortingServiceImpl();
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

  static Stream<Arguments> provideTextForSorting() {
    TextComponent sentenceA = new TextSortingServiceImplTest().createSentence("This", "is", "a", "long", "sentence.");
    TextComponent sentenceB = new TextSortingServiceImplTest().createSentence("Short", "one.");
    TextComponent sentenceC = new TextSortingServiceImplTest().createSentence("Medium", "size", "test.");

    TextComponent inputStructure = new TextSortingServiceImplTest().createTestTextStructure(sentenceA, sentenceB, sentenceC);

    String expected = "Short one. Medium size test. This is a long sentence. ";

    return Stream.of(
            Arguments.of("Standard Sort", inputStructure, expected)
    );
  }

  @ParameterizedTest
  @MethodSource("provideTextForSorting")
  void sortSentencesByLexemeCount(String testName, TextComponent inputStructure, String expected) {
    String result = sortingService.sortSentencesByLexemeCount(inputStructure);

    String normalizedExpected = expected.replaceAll("\\s+", " ").trim();
    String normalizedActual = result.replaceAll("\\s+", " ").trim();

    assertEquals(normalizedExpected, normalizedActual,
            "The sentences must be sorted by the number of lexemes (normalized spacing).");
  }
}