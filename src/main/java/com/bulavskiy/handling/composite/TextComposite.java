package com.bulavskiy.handling.composite;

import java.util.ArrayList;
import java.util.List;

public class TextComposite implements TextComponent{

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
    for(TextComponent component : components){
      sb.append(component.toString());

      if (type == TextType.TEXT) {
        sb.append("\n\t");
      } else if (type == TextType.PARAGRAPH) {
        sb.append(" ");
      } else if (type == TextType.SENTENCE) {
        sb.append(" ");
      }
    }
    return sb.toString();
  }

  @Override
  public int count() {
    int a = 0;
    for (TextComponent component : components) {
      a += component.count();
    }
    return a;
  }
}
