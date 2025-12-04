package com.bulavskiy.handling.composite;


public interface TextComponent {
  TextType getType();
  void add(TextComponent component);
  void remove(TextComponent component);
  String toString();
  int count();
}
