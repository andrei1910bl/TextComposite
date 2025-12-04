package com.bulavskiy.handling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.bulavskiy.handling.composite.TextComponent;
import com.bulavskiy.handling.exception.CustomException;
import com.bulavskiy.handling.parser.AbstractTextParser;
import com.bulavskiy.handling.parser.ChainBuilder;
import com.bulavskiy.handling.reader.CustomFileReader;
import com.bulavskiy.handling.reader.impl.CustomFileReaderImpl;
import com.bulavskiy.handling.service.TextAnalysisService;
import com.bulavskiy.handling.service.TextModificationService;
import com.bulavskiy.handling.service.TextSortingService;
import com.bulavskiy.handling.service.impl.TextAnalysisServiceImpl;
import com.bulavskiy.handling.service.impl.TextModificationServiceImpl;
import com.bulavskiy.handling.service.impl.TextSortingServiceImpl;

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