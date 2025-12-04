package org.example.service.impl;

import org.example.composite.SymbolLeaf;
import org.example.composite.TextComponent;
import org.example.composite.TextComposite;
import org.example.composite.TextType;
import org.example.service.TextModificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextModificationServiceImplTest {

  private TextModificationService modificationService;

  @BeforeEach
  void setUp() {
    modificationService = new TextModificationServiceImpl();
  }

  private TextComposite createSymbol(char symbol, TextType type) {
    TextComposite comp = new TextComposite(type);
    comp.add(new SymbolLeaf(symbol, TextType.SYMBOL));
    return comp;
  }

  private TextComposite createWord(String wordValue) {
    TextComposite wordComp = new TextComposite(TextType.WORD);
    for (char c : wordValue.toCharArray()) {
      wordComp.add(new SymbolLeaf(c, TextType.SYMBOL));
    }
    return wordComp;
  }

  private TextComposite createLexeme(String wordValue, boolean isPunctuationLast) {
    TextComposite lexemeComp = new TextComposite(TextType.LEXEME);
    lexemeComp.add(createWord(wordValue));
    if (isPunctuationLast) {
      lexemeComp.add(createSymbol('.', TextType.PUNCTUATION));
    }
    return lexemeComp;
  }

  private TextComponent createSentence(List<TextComponent> lexemes) {
    TextComposite sentenceComp = new TextComposite(TextType.SENTENCE);
    for (TextComponent lexeme : lexemes) {
      sentenceComp.add(lexeme);
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
  void swapFirstAndLastWords() {
    TextComponent l1 = createLexeme("First", false);
    TextComponent l2 = createLexeme("is", false);
    TextComponent l3 = createLexeme("the", false);
    TextComponent l4 = createLexeme("Last", true);

    TextComponent sentence = createSentence(List.of(l1, l2, l3, l4));
    TextComponent text = createTestTextStructure(sentence);

    String expectedBefore = "First is the Last. ";
    String expectedAfter = "Last. is the First ";

    assertEquals(expectedBefore.trim(), text.toString().trim());

    modificationService.swapFirstAndLastWords(text);

    assertEquals(expectedAfter.trim(), text.toString().trim());
  }

}