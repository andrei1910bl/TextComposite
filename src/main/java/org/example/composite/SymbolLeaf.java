package org.example.composite;

import org.example.exception.CustomException;

public class SymbolLeaf implements TextComponent {
  private char symbol;
  private TextType type;

  public SymbolLeaf(char symbol) {
    this.symbol = symbol;
  }

  public SymbolLeaf(char symbol, TextType type) {
    this.symbol = symbol;
    this.type = type;
  }

  @Override
  public void add(TextComponent component) {
//    throw new CustomException("Cannot add leaf");
  }

  @Override
  public void remove(TextComponent component) {
//    throw new CustomException("Cannot remove from leaf");
  }

  @Override
  public String toString() {
    return Character.toString(symbol);
  }

  @Override
  public int count() {
    return 1;
  }
}
