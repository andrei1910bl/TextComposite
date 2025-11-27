package org.example.composite;

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
  public String show() {
    return Character.toString(symbol);
  }

  @Override
  public int count() {
    return 1;
  }
}
