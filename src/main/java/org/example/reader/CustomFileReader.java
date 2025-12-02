package org.example.reader;

import org.example.exception.CustomException;

import java.util.List;

public interface CustomFileReader {
  String readFile(String path) throws CustomException;
}
