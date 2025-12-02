package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.composite.SymbolLeaf;
import org.example.composite.TextComponent;
import org.example.composite.TextType;
import org.example.exception.CustomException;
import org.example.parser.AbstractTextParser;
import org.example.parser.ChainBuilder;
import org.example.reader.CustomFileReader;
import org.example.reader.impl.CustomFileReaderImpl;



public class App {
  private static final Logger log = LogManager.getLogger();
    public static void main( String[] args ) throws CustomException {
        SymbolLeaf s1 = new SymbolLeaf('a', TextType.SYMBOL);
        SymbolLeaf s2 = new SymbolLeaf(' ', TextType.SYMBOL);
        SymbolLeaf s3 = new SymbolLeaf('b', TextType.SYMBOL);


        CustomFileReader reader = new CustomFileReaderImpl();
      try {
        String file = reader.readFile("data/text.txt");

//        AbstractTextParser parser = new TextToParagraphParser();
//        parser.parse(file);

        ChainBuilder builder = new ChainBuilder();
        AbstractTextParser parser = builder.buildChain();
        log.info("Parser chain is build");
        TextComponent parsedTextStructure = parser.parse(file);

        String assembledText = parsedTextStructure.toString();
        log.info("Разобранный текст {}", assembledText);

      } catch (CustomException e) {
        throw new CustomException("File not read");
      }


    }
}
