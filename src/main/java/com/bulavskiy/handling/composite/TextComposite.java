package com.bulavskiy.handling.composite;

import java.util.ArrayList;
import java.util.List;

public class TextComposite implements TextComponent {

  private static final String PARAGRAPH_SEPARATOR = "\n\t";
  private static final String WORD_SEPARATOR = " ";

  private List<TextComponent> components = new ArrayList<>();
  private TextType type;

  public TextComposite(TextType type) {
    this.type = type;
  }

  @Override
  public TextType getType() {
    return type;
  }

  public List<TextComponent> getComponents() {
    return components;
  }

  @Override
  public void add(TextComponent component) {
    components.add(component);
  }

  @Override
  public void remove(TextComponent component) {
    components.remove(component);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (TextComponent component : components) {
      sb.append(component.toString());

      if (type == TextType.TEXT) {
        sb.append(PARAGRAPH_SEPARATOR);
      } else if (type == TextType.PARAGRAPH || type == TextType.SENTENCE) {
        sb.append(WORD_SEPARATOR);
      }
    }
    return sb.toString();
  }

  @Override
  public int count() {
    int count = 0;
    for (TextComponent component : components) {
      count += component.count();
    }
    return count;
  }
}