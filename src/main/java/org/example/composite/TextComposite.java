package org.example.composite;

import java.util.ArrayList;
import java.util.List;

public class TextComposite implements TextComponent{

  private List<TextComponent> components = new ArrayList<>();

  @Override
  public String show() {
    StringBuilder sb = new StringBuilder();
    for(TextComponent component : components){
      sb.append(component.show());
    }
    return sb.toString();
  }

  @Override
  public int count() {
    //TODO
  return 0;
  }
}
