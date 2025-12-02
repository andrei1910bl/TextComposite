package org.example.parser;

public class TextRegex {
  public static final String PARAGRAPH_DELIMITER_REGEX = "\\s*\\n\\s*\\n\\s*";
  public static final String SENTENCE_DELIMITER_REGEX = "(?<=[.!?])\\s*";
  public static final String LEXEME_DELIMITER_REGEX = "\\s+";
  public static final String WORD_PUNCTUATION_REGEX = "([\\p{L}0-9]+)|([.,!?:;\"'()\\-]+)";
}
