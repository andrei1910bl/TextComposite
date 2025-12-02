package org.example.parser;

import org.example.composite.TextComponent;

public abstract class AbstractTextParser {
  protected AbstractTextParser nextParser;

  public void setNextParser(AbstractTextParser nextParser){
    this.nextParser = nextParser;
  }

  public abstract TextComponent parse(String text);
}
