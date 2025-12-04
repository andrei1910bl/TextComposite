package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.composite.TextComponent;
import org.example.exception.CustomException;
import org.example.parser.AbstractTextParser;
import org.example.parser.ChainBuilder;
import org.example.reader.CustomFileReader;
import org.example.reader.impl.CustomFileReaderImpl;
import org.example.service.TextAnalysisService;
import org.example.service.TextModificationService;
import org.example.service.TextSortingService;
import org.example.service.impl.TextAnalysisServiceImpl;
import org.example.service.impl.TextModificationServiceImpl;
import org.example.service.impl.TextSortingServiceImpl;

public class App {
  private static final Logger log = LogManager.getLogger();

  public static void main( String[] args ) {
    CustomFileReader reader = new CustomFileReaderImpl();

    try {
      String fileContent = reader.readFile("data/text.txt");
      log.info("File read successfully.");

      ChainBuilder builder = new ChainBuilder();
      AbstractTextParser parser = builder.buildChain();
      log.info("Parser chain is built.");

      TextComponent parsedTextStructure = parser.parse(fileContent);
      log.info("Text parsing complete.");

      String assembledText = parsedTextStructure.toString();
      log.info("--- Reconstructed Text ---\n{}", assembledText);
      log.info("Total symbol count: {}", parsedTextStructure.count());

      TextAnalysisService analysisService = new TextAnalysisServiceImpl();
      TextSortingService sortingService = new TextSortingServiceImpl();
      TextModificationService modificationService = new TextModificationServiceImpl();

      int maxCommonWords = analysisService.findMaxSentencesWithCommonWord(parsedTextStructure);
      log.info("Analysis Result: Maximum number of sentences sharing a common word: {}", maxCommonWords);

      log.info("--- 🔄 Starting sorting service ---");
      String sortedText = sortingService.sortSentencesByLexemeCount(parsedTextStructure);
      log.info("Text sorted by lexeme count per sentence:\n{}", sortedText);

      log.info("--- ✏️ Starting modification service ---");

      modificationService.swapFirstAndLastWords(parsedTextStructure);

      String modifiedText = parsedTextStructure.toString();
      log.info("Text after modification (swapping first and last word):\n{}", modifiedText);

    } catch (CustomException e) {
      log.info("File reading or parsing failed.", e);
    }
  }
}