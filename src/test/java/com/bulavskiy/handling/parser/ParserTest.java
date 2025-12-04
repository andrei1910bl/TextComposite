package com.bulavskiy.handling.parser;

import com.bulavskiy.handling.composite.TextComponent;
import com.bulavskiy.handling.composite.TextComposite;
import com.bulavskiy.handling.composite.TextType;
import com.bulavskiy.handling.parser.*;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

  private AbstractTextParser buildParserChain() {
    AbstractTextParser lexemeToWordSymbol = new LexemeToWordSymbolParser();
    AbstractTextParser sentenceToLexeme = new SentenceToLexemeParser();
    AbstractTextParser paragraphToSentence = new ParagraphToSentenceParser();
    AbstractTextParser textToParagraph = new TextToParagraphParser();

    sentenceToLexeme.setNextParser(lexemeToWordSymbol);
    paragraphToSentence.setNextParser(sentenceToLexeme);
    textToParagraph.setNextParser(paragraphToSentence);

    return textToParagraph;
  }

  @Test
  void testLexemeToWordSymbolParser_Word() {
    LexemeToWordSymbolParser parser = new LexemeToWordSymbolParser();
    TextComposite result = (TextComposite) parser.parse("Hello");
    List<TextComponent> components = result.getComponents();

    assertAll(
            () -> assertEquals(TextType.LEXEME, result.getType()),
            () -> assertEquals(1, components.size()),
            () -> assertEquals(TextType.WORD, components.get(0).getType()),
            () -> assertEquals("Hello", components.get(0).toString().trim())
    );
  }

  @Test
  void testLexemeToWordSymbolParser_Punctuation() {
    LexemeToWordSymbolParser parser = new LexemeToWordSymbolParser();
    TextComposite result = (TextComposite) parser.parse("!");
    List<TextComponent> components = result.getComponents();

    assertAll(
            () -> assertEquals(TextType.LEXEME, result.getType()),
            () -> assertEquals(1, components.size()), // One component: PUNCTUATION
            () -> assertEquals(TextType.PUNCTUATION, components.get(0).getType()),
            () -> assertEquals("!", components.get(0).toString().trim())
    );
  }

  @Test
  void testLexemeToWordSymbolParser_LexemeWithPunctuation() {
    LexemeToWordSymbolParser parser = new LexemeToWordSymbolParser();
    TextComposite result = (TextComposite) parser.parse("World!");
    List<TextComponent> components = result.getComponents();

    assertAll(
            () -> assertEquals(TextType.LEXEME, result.getType()),
            () -> assertEquals(2, components.size()), // Two components: WORD, PUNCTUATION
            () -> assertEquals(TextType.WORD, components.get(0).getType()),
            () -> assertEquals(TextType.PUNCTUATION, components.get(1).getType()),
            () -> assertEquals("World!", result.toString())
    );
  }

  @Test
  void testSentenceToLexemeParser() {
    SentenceToLexemeParser parser = new SentenceToLexemeParser();
    parser.setNextParser(new LexemeToWordSymbolParser());

    TextComposite result = (TextComposite) parser.parse("Hello world test.");
    List<TextComponent> lexemes = result.getComponents();

    assertAll(
            () -> assertEquals(TextType.SENTENCE, result.getType()),
            () -> assertEquals(3, lexemes.size()),
            () -> assertEquals(TextType.LEXEME, lexemes.get(0).getType()),
            () -> assertEquals(TextType.LEXEME, lexemes.get(1).getType()),
            () -> assertEquals(TextType.LEXEME, lexemes.get(2).getType())
    );
  }

  @Test
  void testParagraphToSentenceParser() {
    ParagraphToSentenceParser parser = new ParagraphToSentenceParser();

    AbstractTextParser lexemeToWordSymbol = new LexemeToWordSymbolParser();
    AbstractTextParser sentenceToLexeme = new SentenceToLexemeParser();
    sentenceToLexeme.setNextParser(lexemeToWordSymbol);
    parser.setNextParser(sentenceToLexeme);

    TextComposite result = (TextComposite) parser.parse("Sentence 1. Sentence 2. Sentence 3.");
    List<TextComponent> sentences = result.getComponents();

    assertAll(
            () -> assertEquals(TextType.PARAGRAPH, result.getType()),
            () -> assertEquals(3, sentences.size()),
            () -> assertEquals(TextType.SENTENCE, sentences.get(0).getType()),
            () -> assertEquals("Sentence 1. Sentence 2. Sentence 3.",
                    result.toString().replaceAll("\\s+", " ").trim())
    );
  }

  @Test
  void testTextToParagraphParser() {
    TextToParagraphParser parser = new TextToParagraphParser();
    parser.setNextParser(new ParagraphToSentenceParser());

    TextComposite result = (TextComposite) parser.parse("Paragraph 1.\n\nParagraph 2.");
    List<TextComponent> paragraphs = result.getComponents();

    assertAll(
            () -> assertEquals(TextType.TEXT, result.getType()),
            () -> assertEquals(2, paragraphs.size()),
            () -> assertEquals(TextType.PARAGRAPH, paragraphs.get(0).getType())
    );
  }

  @Test
  void testFullChain_ParseStructure() {
    AbstractTextParser parser = buildParserChain();

    String inputText =
            "Hello, world! First line.\n\n" +
                    "Second paragraph. Test.";

    TextComposite root = (TextComposite) parser.parse(inputText);

    List<TextComponent> paragraphs = root.getComponents();
    TextComposite p1 = (TextComposite) paragraphs.get(0);
    TextComposite p2 = (TextComposite) paragraphs.get(1);

    assertAll(
            () -> assertEquals(TextType.TEXT, root.getType()),
            () -> assertEquals(2, paragraphs.size()),

            () -> assertEquals(2, p1.getComponents().size()),

            () -> assertEquals("Hello, world! First line. ",
                    p1.toString().replaceAll("\\s+", " ").trim() + " "), // Добавляем пробел в конце, если p1.toString() заканчивается пробелом

            () -> assertEquals(2, p2.getComponents().size())
    );
  }
}