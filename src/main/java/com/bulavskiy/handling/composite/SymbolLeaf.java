package com.bulavskiy.handling.composite;

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
  public TextType getType() {
    return type;
  }

  @Override
  public void add(TextComponent component) {
  }

  @Override
  public void remove(TextComponent component) {
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
