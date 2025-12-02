package org.example.composite;

import org.example.exception.CustomException;

public interface TextComponent {
  void add(TextComponent component);
  void remove(TextComponent component);
  String toString();
  int count();
}
